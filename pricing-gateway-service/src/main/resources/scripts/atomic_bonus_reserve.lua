-- Atomic Lua Script for Kaspi Monthly Bonus Limit and Split-Transaction logic
-- KEYS[1]: Monthly bonus limit key (e.g. user:1001:bonus_month:2026-11)
-- ARGV[1]: Requested bonus amount to be awarded
-- ARGV[2]: Hard monthly bonus cap (e.g. 30000)
-- ARGV[3]: Key TTL in seconds (e.g. 3024000 = 35 days)
-- Returns: Array [awarded_bonuses, remaining_limit, status_code]

local requested = tonumber(ARGV[1])
local cap = tonumber(ARGV[2])
local ttl = tonumber(ARGV[3]) or 3024000

local current_spent = tonumber(redis.call('GET', KEYS[1]))
if not current_spent then
    current_spent = 0
end

local available_limit = cap - current_spent

if available_limit <= 0 then
    -- Limit already fully reached before this transaction
    return { "0", "0", "LIMIT_REACHED_ZERO_BONUS" }
end

if requested <= available_limit then
    -- Full bonus can be awarded within monthly cap
    local new_spent = current_spent + requested
    redis.call('SET', KEYS[1], tostring(new_spent))
    redis.call('EXPIRE', KEYS[1], ttl)
    local remaining = available_limit - requested
    return { tostring(requested), tostring(remaining), "FULL_AWARDED" }
else
    -- Split transaction: partial bonus up to remaining limit
    local awarded = available_limit
    redis.call('SET', KEYS[1], tostring(cap))
    redis.call('EXPIRE', KEYS[1], ttl)
    return { tostring(awarded), "0", "SPLIT_TRANSACTION" }
end

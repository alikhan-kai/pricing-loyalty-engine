package kz.kaspi.pricing.rule;

import org.mvel2.MVEL;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Высокопроизводительный сервис компиляции выражений MVEL в байткод JVM.
 */
@Service
public class MvelRuleCompiler {

    private final Map<String, Serializable> compiledExpressionCache = new ConcurrentHashMap<>();

    /**
     * Компилирует или возвращает из кэша скомпилированное выражение.
     */
    public Serializable compile(String expression) {
        if (expression == null || expression.isBlank()) {
            return null;
        }
        return compiledExpressionCache.computeIfAbsent(expression, MVEL::compileExpression);
    }

    /**
     * Вычисляет логическое условие правила на основе контекстных параметров запроса.
     */
    public boolean evaluateCondition(String expression, Map<String, Object> context) {
        if (expression == null || expression.isBlank()) {
            return true;
        }
        Serializable compiled = compile(expression);
        Object result = MVEL.executeExpression(compiled, context);
        return Boolean.TRUE.equals(result);
    }

    /**
     * Очищает кэш скомпилированных выражений при обновлении правил.
     */
    public void clearCache() {
        compiledExpressionCache.clear();
    }
}

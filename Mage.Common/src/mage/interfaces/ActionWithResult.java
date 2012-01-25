package mage.interfaces;

/**
 * /**
 * Light weight action interface
 * For executing actions without any context.
 *
 * @param <T> Type to return as a result of execution.
 *
 * @author noxx
 */
public interface ActionWithResult<T> {

    /**
     * Executes and returns result.
     * @return
     */
    public T execute();

    /**
     * Returns negative result specific for type <T>.
     * @return
     */
    public T negativeResult();
}

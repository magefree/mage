package mage.util.immutableWrappers;

public interface ImmutableObject {
    default boolean throwImmutableError() {
        throw new UnsupportedOperationException("This object is immutable");
    }

}

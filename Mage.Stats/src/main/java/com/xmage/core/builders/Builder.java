package com.xmage.core.builders;

public abstract class Builder<E> {

    protected E entity;

    protected Builder() {
        //entity = E.class.newInstance();
    }

    abstract protected void validate();

    public final E build() {
        validate();
        return entity;
    }
}

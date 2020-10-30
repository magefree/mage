package mage.utils;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class FluentBuilder<ToBuild, RealBuilder extends FluentBuilder<ToBuild, RealBuilder>> {

    final ArrayList<Consumer<RealBuilder>> buildSequence;
    private final Supplier<RealBuilder> newReference;

    protected FluentBuilder(Supplier<RealBuilder> newReference) {
        this.buildSequence = new ArrayList<>();
        this.newReference = newReference;
    }

    private RealBuilder copy() {
        final RealBuilder realBuilder = newReference.get();
        realBuilder.buildSequence.addAll(buildSequence);
        return realBuilder;
    }

    protected abstract ToBuild makeValue();

    public RealBuilder with(Consumer<RealBuilder> consumer) {
        final RealBuilder nextBuilder = this.copy();
        nextBuilder.buildSequence.add(consumer);
        return nextBuilder;
    }

    public ToBuild build() {
        final RealBuilder instance = this.copy();
        instance.buildSequence.forEach(c -> c.accept(instance));
        return instance.makeValue();
    }
}

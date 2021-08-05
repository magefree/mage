package mage.utils;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A base class for fluent, immutable, composable builders.
 *
 * @see <a href="https://github.com/fburato/functionalutils/blob/master/utils/src/main/java/com/github/fburato/functionalutils/utils/Builder.java">Builder</a>
 */
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

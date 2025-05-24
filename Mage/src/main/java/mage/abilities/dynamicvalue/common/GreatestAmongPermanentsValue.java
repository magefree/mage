package mage.abilities.dynamicvalue.common;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * Dynamic value for "greatest [quality] among [permanent filter]"
 * For the most common ones, add an entry in the Instanced enum instead of using new GreatestAmongPermanentsValue(...).
 *
 * @author Susucr
 */
public class GreatestAmongPermanentsValue implements DynamicValue {

    public enum Instanced implements DynamicValue {
        PowerControlledCreatures(Quality.Power, StaticFilters.FILTER_CONTROLLED_CREATURES),
        PowerOtherControlledCreatures(Quality.Power, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES),
        ToughnessControlledCreatures(Quality.Toughness, StaticFilters.FILTER_CONTROLLED_CREATURES),
        ToughnessOtherControlledCreatures(Quality.Toughness, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES),
        PowerOrToughnessOtherControlledCreatures(Quality.PowerOrToughness, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES),
        ManaValueControlledPermanents(Quality.ManaValue, StaticFilters.FILTER_CONTROLLED_PERMANENTS),
        ManaValueControlledCreatures(Quality.ManaValue, StaticFilters.FILTER_CONTROLLED_CREATURES),
        ManaValueControlledArtifacts(Quality.ManaValue, StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS),
        ManaValueOtherControlledPermanents(Quality.ManaValue, StaticFilters.FILTER_OTHER_CONTROLLED_PERMANENTS),
        ManaValueOtherControlledCreatures(Quality.ManaValue, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES),
        ManaValueOtherControlledArtifacts(Quality.ManaValue, StaticFilters.FILTER_OTHER_CONTROLLED_ARTIFACTS);

        private final GreatestAmongPermanentsValue inner;
        private final Hint hint;

        Instanced(Quality quality, FilterPermanent filter) {
            this.inner = new GreatestAmongPermanentsValue(quality, filter);
            this.hint = this.inner.getHint();
        }

        @Override
        public int calculate(Game game, Ability sourceAbility, Effect effect) {
            return this.inner.calculate(game, sourceAbility, effect);
        }

        @Override
        public Instanced copy() {
            return this;
        }

        @Override
        public String getMessage() {
            return this.inner.getMessage();
        }

        @Override
        public String toString() {
            return this.inner.toString();
        }

        public Hint getHint() {
            return this.hint;
        }
    }

    public enum Quality {
        Power("power", Permanent::getPower),
        Toughness("toughness", Permanent::getToughness),
        ManaValue("mana value", Permanent::getManaValue),
        PowerOrToughness("power and/or toughness",
                (Permanent permanent) -> {
                    int power = permanent.getPower().getValue();
                    int toughness = permanent.getToughness().getValue();
                    return Math.max(power, toughness);
                }
        );

        final String text;
        final Function<Stream<Permanent>, IntStream> mapToQuality;

        Quality(String text, Function<Permanent, MageInt> permanentToMageInt) {
            this.text = text;
            this.mapToQuality = (Stream<Permanent> stream) -> stream.map(permanentToMageInt).mapToInt(MageInt::getValue);
        }

        Quality(String text, ToIntFunction<Permanent> permanentToInt) {
            this.text = text;
            this.mapToQuality = (Stream<Permanent> stream) -> stream.mapToInt(permanentToInt);
        }
    }

    private final Quality quality;
    private final FilterPermanent filter;

    public GreatestAmongPermanentsValue(Quality quality, FilterPermanent filter) {
        this.filter = filter;
        this.quality = quality;
    }

    private GreatestAmongPermanentsValue(final GreatestAmongPermanentsValue value) {
        super();
        this.filter = value.filter.copy();
        this.quality = value.quality;
    }

    @Override
    public GreatestAmongPermanentsValue copy() {
        return new GreatestAmongPermanentsValue(this);
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Stream<Permanent> permanents = game
                .getBattlefield()
                .getActivePermanents(
                        this.filter, sourceAbility.getControllerId(), sourceAbility, game
                ).stream();
        return this.quality.mapToQuality.apply(permanents)
                .max()
                .orElse(0);
    }

    @Override
    public String getMessage() {
        return "the greatest " + quality.text + " among " + this.filter.getMessage();
    }

    @Override
    public String toString() {
        return "X";
    }

    public Hint getHint() {
        return new ValueHint("Greatest " + quality.text + " among " + filter.getMessage(), this);
    }
}
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
 * For the most common ones, add a static entry instead of using new GreatestAmongPermanentsValue(...).
 *
 * @author Susucr
 */
public class GreatestAmongPermanentsValue implements DynamicValue {

    public static final GreatestAmongPermanentsValue POWER_CONTROLLED_CREATURES
            = new GreatestAmongPermanentsValue(Quality.Power, StaticFilters.FILTER_CONTROLLED_CREATURES);
    public static final GreatestAmongPermanentsValue POWER_OTHER_CONTROLLED_CREATURES
            = new GreatestAmongPermanentsValue(Quality.Power, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES);
    public static final GreatestAmongPermanentsValue TOUGHNESS_CONTROLLED_CREATURES
            = new GreatestAmongPermanentsValue(Quality.Toughness, StaticFilters.FILTER_CONTROLLED_CREATURES);
    public static final GreatestAmongPermanentsValue TOUGHNESS_OTHER_CONTROLLED_CREATURES
            = new GreatestAmongPermanentsValue(Quality.Toughness, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES);
    public static final GreatestAmongPermanentsValue POWER_OR_TOUGHNESS_OTHER_CONTROLLED_CREATURES
            = new GreatestAmongPermanentsValue(Quality.PowerOrToughness, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES);
    public static final GreatestAmongPermanentsValue MANAVALUE_CONTROLLED_CREATURES
            = new GreatestAmongPermanentsValue(Quality.ManaValue, StaticFilters.FILTER_CONTROLLED_CREATURES);
    public static final GreatestAmongPermanentsValue MANAVALUE_OTHER_CONTROLLED_CREATURES
            = new GreatestAmongPermanentsValue(Quality.ManaValue, StaticFilters.FILTER_OTHER_CONTROLLED_CREATURES);
    public static final GreatestAmongPermanentsValue MANAVALUE_CONTROLLED_ARTIFACTS
            = new GreatestAmongPermanentsValue(Quality.ManaValue, StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS);
    public static final GreatestAmongPermanentsValue MANAVALUE_OTHER_CONTROLLED_ARTIFACTS
            = new GreatestAmongPermanentsValue(Quality.ManaValue, StaticFilters.FILTER_OTHER_CONTROLLED_ARTIFACTS);
    public static final GreatestAmongPermanentsValue MANAVALUE_CONTROLLED_PERMANENTS
            = new GreatestAmongPermanentsValue(Quality.ManaValue, StaticFilters.FILTER_CONTROLLED_PERMANENTS);
    public static final GreatestAmongPermanentsValue MANAVALUE_OTHER_CONTROLLED_PERMANENTS
            = new GreatestAmongPermanentsValue(Quality.ManaValue, StaticFilters.FILTER_OTHER_CONTROLLED_PERMANENTS);

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
        this.filter = value.filter;
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
        ;
    }
}
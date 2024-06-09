package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.Set;

/**
 * @author TheElk801
 */
public final class EllywickTumblestrumEmblem extends Emblem {

    // −7: You get an emblem with "Creatures you control have trample and haste and get +2/+2 for each differently named dungeon you've completed."
    public EllywickTumblestrumEmblem() {
        super("Emblem Ellywick");
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfGame,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.EndOfGame,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and haste"));
        ability.addEffect(new BoostControlledEffect(
                EllywickTumblestrumEmblemValue.instance,
                EllywickTumblestrumEmblemValue.instance,
                Duration.EndOfGame
        ).setText("and get +2/+2 for each differently named dungeon you've completed"));
        this.getAbilities().add(ability.addHint(EllywickTumblestrumEmblemHint.instance));
    }

    private EllywickTumblestrumEmblem(final EllywickTumblestrumEmblem card) {
        super(card);
    }

    @Override
    public EllywickTumblestrumEmblem copy() {
        return new EllywickTumblestrumEmblem(this);
    }
}

enum EllywickTumblestrumEmblemValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return 2 * CompletedDungeonWatcher.getCompletedNames(sourceAbility.getControllerId(), game).size();
    }

    @Override
    public EllywickTumblestrumEmblemValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }
}

enum EllywickTumblestrumEmblemHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Set<String> names = CompletedDungeonWatcher.getCompletedNames(ability.getControllerId(), game);
        if (names.isEmpty()) {
            return "No dungeons completed";
        }
        return "Completed dungeons: " + String.join(", ", names);
    }

    @Override
    public EllywickTumblestrumEmblemHint copy() {
        return this;
    }
}

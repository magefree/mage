package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.combat.CombatGroup;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PackAttack extends CardImpl {

    public PackAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Attacking creatures get +X/+0 until end of turn, where X is the number of players being attacked.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                PackAttackValue.instance, StaticValue.get(0), Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES, false, null
        ));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private PackAttack(final PackAttack card) {
        super(card);
    }

    @Override
    public PackAttack copy() {
        return new PackAttack(this);
    }
}

enum PackAttackValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getCombat()
                .getGroups()
                .stream()
                .map(CombatGroup::getDefenderId)
                .filter(Objects::nonNull)
                .distinct()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(x -> 1)
                .sum();
    }

    @Override
    public PackAttackValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of players being attacked";
    }

    @Override
    public String toString() {
        return "X";
    }
}

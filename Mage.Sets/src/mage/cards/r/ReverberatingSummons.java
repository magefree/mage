package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReverberatingSummons extends CardImpl {

    public ReverberatingSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // At the beginning of each combat, if you've cast two or more spells this turn, this enchantment becomes a 3/3 Monk creature with haste in addition to its other types until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                TargetController.ANY,
                new BecomesCreatureSourceEffect(
                        new CreatureToken(3, 3, "3/3 Monk creature with haste")
                                .withSubType(SubType.MONK)
                                .withAbility(HasteAbility.getInstance()),
                        CardType.ENCHANTMENT, Duration.EndOfTurn
                ), false
        ).withInterveningIf(ReverberatingSummonsCondition.instance));

        // {1}{R}, Discard your hand, Sacrifice this enchantment: Draw two cards.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(2), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new DiscardHandCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ReverberatingSummons(final ReverberatingSummons card) {
        super(card);
    }

    @Override
    public ReverberatingSummons copy() {
        return new ReverberatingSummons(this);
    }
}

enum ReverberatingSummonsCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(SpellsCastWatcher.class)
                .getCount(source.getControllerId()) >= 2;
    }

    @Override
    public String toString() {
        return "you've cast two or more spells this turn";
    }
}

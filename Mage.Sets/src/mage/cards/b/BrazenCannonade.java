package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author @stwalsh4118
 */
public final class BrazenCannonade extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("an attacking creature you control");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public BrazenCannonade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // Whenever an attacking creature you control dies, Brazen Cannonade deals 2 damage to each opponent.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DamagePlayersEffect(2, TargetController.OPPONENT), false, filter
        ));

        // Raid -- At the beginning of your postcombat main phase, if you attacked with a creature this turn, exile the top card of your library. Until end of combat on your next turn, you may play that card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfPostCombatMainTriggeredAbility(
                        new ExileTopXMayPlayUntilEndOfTurnEffect(
                                1, false, Duration.UntilEndCombatOfYourNextTurn
                        ), TargetController.YOU, false
                ), RaidCondition.instance, "At the beginning of your postcombat main phase, " +
                "if you attacked with a creature this turn, exile the top card of your library. " +
                "Until end of combat on your next turn, you may play that card."
        );
        this.addAbility(ability.setAbilityWord(AbilityWord.RAID).addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private BrazenCannonade(final BrazenCannonade card) {
        super(card);
    }

    @Override
    public BrazenCannonade copy() {
        return new BrazenCannonade(this);
    }
}

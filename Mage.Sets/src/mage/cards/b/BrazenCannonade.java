package mage.cards.b;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.hint.common.RaidHint;
import mage.constants.AbilityWord;
import mage.constants.TargetController;
import mage.filter.common.FilterAttackingCreature;
import mage.watchers.common.PlayerAttackedWatcher;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author @stwalsh4118
 */
public final class BrazenCannonade extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("an attacking creature");

    public BrazenCannonade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");
        

        // Whenever an attacking creature you control dies, Brazen Cannonade deals 2 damage to each opponent.
        Ability ability = new DiesCreatureTriggeredAbility(new DamagePlayersEffect(2, TargetController.OPPONENT), false, filter);
        this.addAbility(ability);

        // Raid -- At the beginning of your postcombat main phase, if you attacked with a creature this turn, exile the top card of your library. Until end of combat on your next turn, you may play that card.
        Ability raidAbility = new ConditionalInterveningIfTriggeredAbility(new BeginningOfPostCombatMainTriggeredAbility(new ExileTopXMayPlayUntilEndOfTurnEffect(1, false, Duration.UntilYourNextEndCombatStep), TargetController.YOU, false),
                RaidCondition.instance,
                "At the beginning of your postcombat main phase, "
                        + "if you attacked with a creature this turn, "
                        + "exile the top card of your library. "
                        + "Until end of combat on your next turn, "
                        + "you may play that card.");
        raidAbility.setAbilityWord(AbilityWord.RAID);
        raidAbility.addHint(RaidHint.instance);
        this.addAbility(raidAbility, new PlayerAttackedWatcher());
    }

    private BrazenCannonade(final BrazenCannonade card) {
        super(card);
    }

    @Override
    public BrazenCannonade copy() {
        return new BrazenCannonade(this);
    }
}

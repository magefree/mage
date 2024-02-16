package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.DiscardsACardOpponentTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.target.common.TargetOpponent;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaidersWake extends CardImpl {

    public RaidersWake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Whenever an opponent discards a card, that player loses 2 life.
        this.addAbility(new DiscardsACardOpponentTriggeredAbility(new LoseLifeTargetEffect(2), false, SetTargetPointer.PLAYER));

        // Raid — At the beginning of your end step, if you attacked this turn, target opponent discards a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new DiscardTargetEffect(1), TargetController.YOU, false), RaidCondition.instance,
                "At the beginning of your end step, if you attacked this turn, target opponent discards a card.");
        ability.addTarget(new TargetOpponent());
        ability.setAbilityWord(AbilityWord.RAID);
        ability.addHint(RaidHint.instance);
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    private RaidersWake(final RaidersWake card) {
        super(card);
    }

    @Override
    public RaidersWake copy() {
        return new RaidersWake(this);
    }
}

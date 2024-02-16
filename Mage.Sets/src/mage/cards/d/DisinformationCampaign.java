package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DisinformationCampaign extends CardImpl {

    public DisinformationCampaign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{B}");

        // When Disinformation Campaign enters the battlefield, you draw a card and each opponent discards a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DrawCardSourceControllerEffect(1, "you"));
        ability.addEffect(new DiscardEachPlayerEffect(
                StaticValue.get(1), false, TargetController.OPPONENT).concatBy("and"));
        this.addAbility(ability);

        // Whenever you surveil, return Disinformation Campaign to its owner's hand.
        this.addAbility(new DisinformationCampaignTriggeredAbility());
    }

    private DisinformationCampaign(final DisinformationCampaign card) {
        super(card);
    }

    @Override
    public DisinformationCampaign copy() {
        return new DisinformationCampaign(this);
    }
}

class DisinformationCampaignTriggeredAbility extends TriggeredAbilityImpl {

    public DisinformationCampaignTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), false);
    }

    private DisinformationCampaignTriggeredAbility(final DisinformationCampaignTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DisinformationCampaignTriggeredAbility copy() {
        return new DisinformationCampaignTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SURVEILED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever you surveil, return {this} to its owner's hand.";
    }
}

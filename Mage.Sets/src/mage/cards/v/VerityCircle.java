package mage.cards.v;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VerityCircle extends CardImpl {

    public VerityCircle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Whenever a creature an opponent controls becomes tapped, if it isn't being declared as an attacker, you may draw a card.
        this.addAbility(new VerityCircleTriggeredAbility());
    }

    private VerityCircle(final VerityCircle card) {
        super(card);
    }

    @Override
    public VerityCircle copy() {
        return new VerityCircle(this);
    }
}

class VerityCircleTriggeredAbility extends TriggeredAbilityImpl {

    VerityCircleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    private VerityCircleTriggeredAbility(final VerityCircleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VerityCircleTriggeredAbility copy() {
        return new VerityCircleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getFlag()) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        Player player = game.getPlayer(controllerId);
        return permanent != null && player != null && permanent.isCreature()
                && player.hasOpponent(permanent.getControllerId(), game);
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls becomes tapped, " +
                "if it isn't being declared as an attacker, you may draw a card.";
    }
}


package mage.cards.s;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class ShapersSanctuary extends CardImpl {

    public ShapersSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Whenever a creature you control becomes the target of a spell or ability an opponent controls, you may draw a card.
        this.addAbility(new CreaturesYouControlTargetedTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private ShapersSanctuary(final ShapersSanctuary card) {
        super(card);
    }

    @Override
    public ShapersSanctuary copy() {
        return new ShapersSanctuary(this);
    }
}

class CreaturesYouControlTargetedTriggeredAbility extends TriggeredAbilityImpl {

    public CreaturesYouControlTargetedTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, true);
    }

    public CreaturesYouControlTargetedTriggeredAbility(final CreaturesYouControlTargetedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CreaturesYouControlTargetedTriggeredAbility copy() {
        return new CreaturesYouControlTargetedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGETED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        Player targetter = game.getPlayer(event.getPlayerId());
        if (permanent != null && permanent.isControlledBy(this.getControllerId()) && permanent.isCreature(game)) {
            Object object = game.getObject(event.getSourceId());
            if (object != null && targetter != null && targetter.hasOpponent(this.getControllerId(), game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control becomes the target of a spell or ability an opponent controls, you may draw a card.";
    }
}

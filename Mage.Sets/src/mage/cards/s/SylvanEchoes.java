
package mage.cards.s;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author Styxo
 */
public final class SylvanEchoes extends CardImpl {

    public SylvanEchoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        // Whenever you clahs and you win, you may draw a card
        this.addAbility(new SylvanEchoesTriggeredAbility());
    }

    public SylvanEchoes(final SylvanEchoes card) {
        super(card);
    }

    @Override
    public SylvanEchoes copy() {
        return new SylvanEchoes(this);
    }
}

class SylvanEchoesTriggeredAbility extends TriggeredAbilityImpl {

    public SylvanEchoesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), true);
    }

    public SylvanEchoesTriggeredAbility(final SylvanEchoesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SylvanEchoesTriggeredAbility copy() {
        return new SylvanEchoesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CLASHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getData().equals("controller") && event.getPlayerId().equals(getControllerId())
                || event.getData().equals("opponent") && event.getTargetId().equals(getControllerId());
    }

    @Override
    public String getRule() {
        return "Whenever you clash and you win, " + super.getRule();
    }
}

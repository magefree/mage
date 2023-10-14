package mage.cards.b;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Backfir3
 */
public final class Bereavement extends CardImpl {

    public Bereavement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");


        // Whenever a green creature dies, its controller discards a card.
        this.addAbility(new BereavementTriggeredAbility());
    }

    private Bereavement(final Bereavement card) {
        super(card);
    }

    @Override
    public Bereavement copy() {
        return new Bereavement(this);
    }
}

class BereavementTriggeredAbility extends TriggeredAbilityImpl {

    BereavementTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DiscardTargetEffect(1));
    }

    private BereavementTriggeredAbility(final BereavementTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BereavementTriggeredAbility copy() {
        return new BereavementTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent)event).isDiesEvent()) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null && permanent.isCreature(game) && permanent.getColor(game).isGreen()) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getControllerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a green creature dies, its controller discards a card.";
    }
}

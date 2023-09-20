
package mage.cards.l;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.continuous.DontLoseByZeroOrLessLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author emerald000
 */
public final class LichsTomb extends CardImpl {

    public LichsTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // You don't lose the game for having 0 or less life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontLoseByZeroOrLessLifeEffect(Duration.WhileOnBattlefield)));

        // Whenever you lose life, sacrifice a permanent for each 1 life you lost.
        this.addAbility(new LichsTombTriggeredAbility());
    }

    private LichsTomb(final LichsTomb card) {
        super(card);
    }

    @Override
    public LichsTomb copy() {
        return new LichsTomb(this);
    }
}

class LichsTombTriggeredAbility extends TriggeredAbilityImpl {

    LichsTombTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeControllerEffect(new FilterPermanent(), 0, ""), false);
    }

    private LichsTombTriggeredAbility(final LichsTombTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LichsTombTriggeredAbility copy() {
        return new LichsTombTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            ((SacrificeEffect) this.getEffects().get(0)).setAmount(StaticValue.get(event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you lose life, sacrifice a permanent for each 1 life you lost.";
    }
}

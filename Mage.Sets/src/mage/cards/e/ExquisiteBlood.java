
package mage.cards.e;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 * @author noxx
 */
public final class ExquisiteBlood extends CardImpl {

    public ExquisiteBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{B}");


        // Whenever an opponent loses life, you gain that much life.
        ExquisiteBloodTriggeredAbility ability = new ExquisiteBloodTriggeredAbility();
        this.addAbility(ability);
    }

    private ExquisiteBlood(final ExquisiteBlood card) {
        super(card);
    }

    @Override
    public ExquisiteBlood copy() {
        return new ExquisiteBlood(this);
    }
}

class ExquisiteBloodTriggeredAbility extends TriggeredAbilityImpl {

    public ExquisiteBloodTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private ExquisiteBloodTriggeredAbility(final ExquisiteBloodTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ExquisiteBloodTriggeredAbility copy() {
        return new ExquisiteBloodTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            this.getEffects().clear();
            this.addEffect(new GainLifeEffect(event.getAmount()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent loses life, you gain that much life.";
    }
}

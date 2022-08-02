
package mage.cards.h;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class HornOfGreed extends CardImpl {

    public HornOfGreed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Whenever a player plays a land, that player draws a card.
        this.addAbility(new HornOfGreedAbility());
    }

    private HornOfGreed(final HornOfGreed card) {
        super(card);
    }

    @Override
    public HornOfGreed copy() {
        return new HornOfGreed(this);
    }
}

class HornOfGreedAbility extends TriggeredAbilityImpl {

    public HornOfGreedAbility() {
        super(Zone.BATTLEFIELD, new DrawCardTargetEffect(1), false);
        setTriggerPhrase("Whenever a player plays a land, ");
    }

    public HornOfGreedAbility(final HornOfGreedAbility ability) {
        super(ability);
    }

    @Override
    public HornOfGreedAbility copy() {
        return new HornOfGreedAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LAND_PLAYED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
        return true;
    }
}

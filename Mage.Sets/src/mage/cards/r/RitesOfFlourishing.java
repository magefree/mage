
package mage.cards.r;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 * @author nantuko
 */
public final class RitesOfFlourishing extends CardImpl {

    public RitesOfFlourishing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");


        // At the beginning of each player's draw step, that player draws an additional card.
        this.addAbility(new RitesOfFlourishingAbility());

        // Each player may play an additional land on each of their turns.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlayAdditionalLandsAllEffect()));
    }

    private RitesOfFlourishing(final RitesOfFlourishing card) {
        super(card);
    }

    @Override
    public RitesOfFlourishing copy() {
        return new RitesOfFlourishing(this);
    }
}

class RitesOfFlourishingAbility extends TriggeredAbilityImpl {

    public RitesOfFlourishingAbility() {
        super(Zone.BATTLEFIELD, new DrawCardTargetEffect(1));
    }

    private RitesOfFlourishingAbility(final RitesOfFlourishingAbility ability) {
        super(ability);
    }

    @Override
    public RitesOfFlourishingAbility copy() {
        return new RitesOfFlourishingAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
        return true;
    }

    @Override
    public String getRule() {
        return "At the beginning of each player's draw step, that player draws an additional card.";
    }

}

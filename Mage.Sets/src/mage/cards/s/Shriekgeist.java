
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public final class Shriekgeist extends CardImpl {

    public Shriekgeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Shriekgeist deals combat damage to a player, that player puts the top two cards of their library into their graveyard.
        this.addAbility(new ShriekgeistTriggeredAbility());
    }

    private Shriekgeist(final Shriekgeist card) {
        super(card);
    }

    @Override
    public Shriekgeist copy() {
        return new Shriekgeist(this);
    }
}

class ShriekgeistTriggeredAbility extends TriggeredAbilityImpl {

    public ShriekgeistTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MillCardsTargetEffect(2));
    }

    public ShriekgeistTriggeredAbility(final ShriekgeistTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ShriekgeistTriggeredAbility copy() {
        return new ShriekgeistTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        if (damageEvent.isCombatDamage() && event.getSourceId().equals(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, that player mills two cards.";
    }
}

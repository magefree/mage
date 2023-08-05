
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 * @author mluds
 */
public final class EdricSpymasterOfTrest extends CardImpl {

    public EdricSpymasterOfTrest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a creature deals combat damage to one of your opponents, its controller may draw a card.
        this.addAbility(new EdricSpymasterOfTrestTriggeredAbility());
    }

    private EdricSpymasterOfTrest(final EdricSpymasterOfTrest card) {
        super(card);
    }

    @Override
    public EdricSpymasterOfTrest copy() {
        return new EdricSpymasterOfTrest(this);
    }
}

class EdricSpymasterOfTrestTriggeredAbility extends TriggeredAbilityImpl {

    public EdricSpymasterOfTrestTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardTargetEffect(1, true), false);
    }

    public EdricSpymasterOfTrestTriggeredAbility(final EdricSpymasterOfTrestTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EdricSpymasterOfTrestTriggeredAbility copy() {
        return new EdricSpymasterOfTrestTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((DamagedPlayerEvent) event).isCombatDamage() && 
                game.getOpponents(this.controllerId).contains(event.getPlayerId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(game.getPermanent(event.getSourceId()).getControllerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to one of your opponents, its controller may draw a card";
    }
}

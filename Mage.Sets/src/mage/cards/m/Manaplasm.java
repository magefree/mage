
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public final class Manaplasm extends CardImpl {

    public Manaplasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.OOZE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell, Manaplasm gets +X/+X until end of turn, where X is that spell's converted mana cost.
        this.addAbility(new ManaplasmAbility());
        
    }

    private Manaplasm(final Manaplasm card) {
        super(card);
    }

    @Override
    public Manaplasm copy() {
        return new Manaplasm(this);
    }
}


class ManaplasmAbility extends TriggeredAbilityImpl {

    public ManaplasmAbility() {
        super(Zone.BATTLEFIELD, new InfoEffect("{this} gets +X/+X until end of turn, where X is that spell's mana value"), false);
    }



    public ManaplasmAbility(final ManaplasmAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());   
        if (spell != null && spell.isControlledBy(controllerId)) {
            this.getEffects().remove(0);
            int x = spell.getManaValue();
            this.addEffect(new BoostSourceEffect(x,x, Duration.EndOfTurn));
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell, {this} gets +X/+X until end of turn, where X is that spell's mana value";
    }

    @Override
    public ManaplasmAbility copy() {
        return new ManaplasmAbility(this);
    }
}

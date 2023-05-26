
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public final class MichikoKondaTruthSeeker extends CardImpl {

    public MichikoKondaTruthSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever a source an opponent controls deals damage to you, that player sacrifices a permanent.
        this.addAbility(new MichikoKondaTruthSeekerAbility());
    }

    private MichikoKondaTruthSeeker(final MichikoKondaTruthSeeker card) {
        super(card);
    }

    @Override
    public MichikoKondaTruthSeeker copy() {
        return new MichikoKondaTruthSeeker(this);
    }
}

class MichikoKondaTruthSeekerAbility extends TriggeredAbilityImpl {

    public MichikoKondaTruthSeekerAbility() {
        super(Zone.BATTLEFIELD, new SacrificeEffect(new FilterPermanent(), 1, "that player"), false);
    }

    public MichikoKondaTruthSeekerAbility(final MichikoKondaTruthSeekerAbility ability) {
        super(ability);
    }

    @Override
    public MichikoKondaTruthSeekerAbility copy() {
        return new MichikoKondaTruthSeekerAbility(this);
    }
    
    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(getControllerId())) {
            UUID sourceControllerId = game.getControllerId(event.getSourceId());
            if (sourceControllerId != null && 
                    game.getOpponents(getControllerId()).contains(sourceControllerId)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(sourceControllerId));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source an opponent controls deals damage to you, that player sacrifices a permanent.";
    }
}


package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public final class Dread extends CardImpl {

    public Dread(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}{B}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Fear
        this.addAbility(FearAbility.getInstance());
        
        // Whenever a creature deals damage to you, destroy it.
        this.addAbility(new DreadTriggeredAbility());
        
        // When Dread is put into a graveyard from anywhere, shuffle it into its owner's library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibrarySourceEffect()));
    }

    private Dread(final Dread card) {
        super(card);
    }

    @Override
    public Dread copy() {
        return new Dread(this);
    }
}

class DreadTriggeredAbility extends TriggeredAbilityImpl {
    
    DreadTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect(), false);
    }
    
    private DreadTriggeredAbility(final DreadTriggeredAbility ability) {
        super(ability);
    }
    
    @Override
    public DreadTriggeredAbility copy() {
        return new DreadTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }
    
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && permanent.isCreature(game)) {
                this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getRule() {
        return "Whenever a creature deals damage to you, destroy it.";
    }
}

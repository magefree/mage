

package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;
 
/**
 * @author LevelX2
 */
public final class ChronicFlooding extends CardImpl {
 
    public ChronicFlooding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");
        this.subtype.add(SubType.AURA);
 

 
        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
       
        // Whenever enchanted land becomes tapped, its controller puts the top three cards of their library into their graveyard.
        this.addAbility(new ChronicFloodingAbility());
    }
 
    private ChronicFlooding(final ChronicFlooding card) {
        super(card);
    }
 
    @Override
    public ChronicFlooding copy() {
        return new ChronicFlooding(this);
    }
}
 
class ChronicFloodingAbility extends TriggeredAbilityImpl {
    ChronicFloodingAbility() {
        super(Zone.BATTLEFIELD, new MillCardsTargetEffect(3));
    }
 
    ChronicFloodingAbility(final ChronicFloodingAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }
 
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent source = game.getPermanent(this.sourceId);
        if (source != null && source.isAttachedTo(event.getTargetId())) {
            Permanent attached = game.getPermanent(source.getAttachedTo());
            if (attached != null) {
                for (Effect e : getEffects()) {
                    e.setTargetPointer(new FixedTarget(attached.getControllerId()));
                }
                return true;
            }
        }
        return false;
    }
 
    @Override
    public ChronicFloodingAbility copy() {
        return new ChronicFloodingAbility(this);
    }
 
    @Override
    public String getRule() {
        return "Whenever enchanted land becomes tapped, its controller mills three cards.";
    }
}

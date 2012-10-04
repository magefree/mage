
/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.returntoravnica;
 
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;
 
/**
 * @author LevelX2
 */
public class ChronicFlooding extends CardImpl<ChronicFlooding> {
 
    public ChronicFlooding(UUID ownerId) {
        super(ownerId, 32, "Chronic Flooding", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Aura");
 
        this.color.setBlue(true);
 
        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
       
        // Whenever enchanted land becomes tapped, its controller puts the top three cards of his or her library into his or her graveyard.
        this.addAbility(new ChronicFloodingAbility());
    }
 
    public ChronicFlooding(final ChronicFlooding card) {
        super(card);
    }
 
    @Override
    public ChronicFlooding copy() {
        return new ChronicFlooding(this);
    }
}
 
class ChronicFloodingAbility extends TriggeredAbilityImpl<ChronicFloodingAbility> {
    ChronicFloodingAbility() {
        super(Constants.Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(3));
    }
 
    ChronicFloodingAbility(final ChronicFloodingAbility ability) {
        super(ability);
    }
 
    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED) {
            Permanent source = game.getPermanent(this.sourceId);
            if (source != null && source.getAttachedTo().equals(event.getTargetId())) {
                Permanent attached = game.getPermanent(source.getAttachedTo());
                if (attached != null) {
 
                    for (Effect e : getEffects()) {
                        e.setTargetPointer(new FixedTarget(attached.getControllerId()));
                    }
                    return true;
                }
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
        return "Whenever enchanted land becomes tapped, its controller puts the top three cards of his or her library into his or her graveyard.";
    }
}
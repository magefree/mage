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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.PutIntoGraveFromBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Plopman
 */
public class SpreadingAlgae extends CardImpl<SpreadingAlgae> {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Swamp");
    static{
        filter.add(new SubtypePredicate("Swamp"));
    }
    public SpreadingAlgae(UUID ownerId) {
        super(ownerId, 274, "Spreading Algae", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "USG";
        this.subtype.add("Aura");

        this.color.setGreen(true);

        // Enchant Swamp
        
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // When enchanted land becomes tapped, destroy it.
        this.addAbility(new SpreadingAlgaeTriggeredAbility(new DestroyTargetEffect()));
        
        // When Spreading Algae is put into a graveyard from the battlefield, return Spreading Algae to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldTriggeredAbility(new ReturnToHandSourceEffect()));
        
    }

    public SpreadingAlgae(final SpreadingAlgae card) {
        super(card);
    }

    @Override
    public SpreadingAlgae copy() {
        return new SpreadingAlgae(this);
    }
}


class SpreadingAlgaeTriggeredAbility extends TriggeredAbilityImpl<SpreadingAlgaeTriggeredAbility> {

    public SpreadingAlgaeTriggeredAbility(Effect effect) {
        super(Constants.Zone.BATTLEFIELD, effect);
    }

    public SpreadingAlgaeTriggeredAbility(final SpreadingAlgaeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SpreadingAlgaeTriggeredAbility copy() {
        return new SpreadingAlgaeTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED) {
            Permanent enchant = game.getPermanent(sourceId);
            if (enchant != null && enchant.getAttachedTo() != null) {
                if (event.getTargetId().equals(enchant.getAttachedTo())) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(enchant.getAttachedTo()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted permanent becomes tapped, destroy it.";
    }

}
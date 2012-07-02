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
package mage.sets.fifthedition;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class Blight extends CardImpl<Blight> {

    public Blight(UUID ownerId) {
        super(ownerId, 6, "Blight", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}");
        this.expansionSetCode = "5ED";
        this.subtype.add("Aura");

        this.color.setBlack(true);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // When enchanted land becomes tapped, destroy it.
        this.addAbility(new BlightTriggeredAbility());
    }

    public Blight(final Blight card) {
        super(card);
    }

    @Override
    public Blight copy() {
        return new Blight(this);
    }
}

class BlightTriggeredAbility extends TriggeredAbilityImpl<BlightTriggeredAbility> {
    BlightTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DestroyTargetEffect());
    }

    BlightTriggeredAbility(final BlightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED) {
            Permanent enchantment = game.getPermanent(this.sourceId);
            if (enchantment != null && enchantment.getAttachedTo().equals(event.getTargetId())) {
                Permanent attached = game.getPermanent(enchantment.getAttachedTo());
                if (attached != null) {
                    for (Effect e : getEffects()) {
                        e.setTargetPointer(new FixedTarget(attached.getId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public BlightTriggeredAbility copy() {
        return new BlightTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When enchanted land becomes tapped, destroy it.";
    }
}

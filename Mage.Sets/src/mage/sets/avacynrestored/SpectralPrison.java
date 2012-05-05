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
package mage.sets.avacynrestored;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.SkipEnchantedUntapEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author noxx
 */
public class SpectralPrison extends CardImpl<SpectralPrison> {

    public SpectralPrison(UUID ownerId) {
        super(ownerId, 75, "Spectral Prison", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "AVR";
        this.subtype.add("Aura");

        this.color.setBlue(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Detriment));
        
        // Enchanted creature doesn't untap during its controller's untap step.
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new SkipEnchantedUntapEffect()));

        // When enchanted creature becomes the target of a spell, sacrifice Spectral Prison.
        this.addAbility(new SpectralPrisonAbility());
    }

    public SpectralPrison(final SpectralPrison card) {
        super(card);
    }

    @Override
    public SpectralPrison copy() {
        return new SpectralPrison(this);
    }
}

class SpectralPrisonAbility extends TriggeredAbilityImpl<SpectralPrisonAbility> {

    public SpectralPrisonAbility() {
        super(Constants.Zone.BATTLEFIELD, new DestroySourceEffect());
    }

    public SpectralPrisonAbility(final SpectralPrisonAbility ability) {
        super(ability);
    }

    @Override
    public SpectralPrisonAbility copy() {
        return new SpectralPrisonAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TARGETED) {
            Permanent enchantment = game.getPermanent(sourceId);
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                if (event.getTargetId().equals(enchantment.getAttachedTo())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When enchanted creature becomes the target of a spell or ability, destroy {this}.";
    }

}

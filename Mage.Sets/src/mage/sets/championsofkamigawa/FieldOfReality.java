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

package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.AttachmentType;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.EvasionAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CantBlockSourceEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX
 */
public class FieldOfReality extends CardImpl<FieldOfReality> {

    public FieldOfReality(UUID ownerId) {
        super(ownerId, 60, "Field of Reality", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Aura");
        this.color.setBlue(true);
        
        // Enchanted creature can't be blocked by Spirits.
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(new FieldOfRealityEvasionAbility(), AttachmentType.AURA )));
        // {1}{U}: Return Field of Reality to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ReturnToHandSourceEffect(), new ManaCostsImpl("{1}{U}")));
    }

    public FieldOfReality(final FieldOfReality card) {
        super(card);
    }

    @Override
    public FieldOfReality copy() {
        return new FieldOfReality(this);
    }

}

class FieldOfRealityEvasionAbility extends EvasionAbility<FieldOfRealityEvasionAbility> {

    public FieldOfRealityEvasionAbility() {
        this.addEffect(new FieldOfRealityEvasionEffect());
    }

    public FieldOfRealityEvasionAbility(final FieldOfRealityEvasionAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "can't be blocked by Spirits";
    }

    @Override
    public FieldOfRealityEvasionAbility copy() {
        return new FieldOfRealityEvasionAbility(this);
    }
}

class FieldOfRealityEvasionEffect extends CantBlockSourceEffect {

    public FieldOfRealityEvasionEffect() {
        super(Duration.WhileOnBattlefield);
    }

    public FieldOfRealityEvasionEffect(final FieldOfRealityEvasionEffect effect) {
        super(effect);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return !blocker.hasSubtype("Spirit") ;
    }

    @Override
    public FieldOfRealityEvasionEffect copy() {
        return new FieldOfRealityEvasionEffect(this);
    }
}
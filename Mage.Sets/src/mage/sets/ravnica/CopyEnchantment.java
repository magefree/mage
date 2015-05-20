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
package mage.sets.ravnica;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author LevelX2
 */
public class CopyEnchantment extends CardImpl {

    public CopyEnchantment(UUID ownerId) {
        super(ownerId, 42, "Copy Enchantment", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "RAV";


        // You may have Copy Enchantment enter the battlefield as a copy of any enchantment on the battlefield.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new EntersBattlefieldEffect(
                new CopyEnchantmentEffect(new FilterEnchantmentPermanent()),
                "You may have {this} enter the battlefield as a copy of any enchantment on the battlefield",
                true));
        this.addAbility(ability);        
    }

    public CopyEnchantment(final CopyEnchantment card) {
        super(card);
    }

    @Override
    public CopyEnchantment copy() {
        return new CopyEnchantment(this);
    }
}

class CopyEnchantmentEffect extends CopyPermanentEffect {

    public CopyEnchantmentEffect(FilterPermanent filter) {
        super(filter, new EmptyApplyToPermanent());
    }
    
    public CopyEnchantmentEffect(final CopyEnchantmentEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            if (super.apply(game, source)) {
                Permanent permanentToCopy = getBluePrintPermanent();
                if (permanentToCopy != null) {
                    if (permanentToCopy.getSubtype().contains("Aura")) {
                        Target target = getBluePrintPermanent().getSpellAbility().getTargets().get(0);
                        Outcome auraOutcome = Outcome.BoostCreature;
                        Ability: for (Ability ability: getBluePrintPermanent().getAbilities()) {
                            if (ability instanceof SpellAbility) {
                                for (Effect effect: ability.getEffects()) {
                                    if (effect instanceof AttachEffect) {
                                        auraOutcome = effect.getOutcome();
                                        break Ability;
                                    }
                                }
                            }
                        }
                        if (controller.choose(auraOutcome, target, source.getSourceId(), game)) {
                            UUID targetId = target.getFirstTarget();
                            Permanent targetPermanent = game.getPermanent(targetId);
                            Player targetPlayer = game.getPlayer(targetId);
                            if (targetPermanent != null) {
                                targetPermanent.addAttachment(sourcePermanent.getId(), game);
                            } else if (targetPlayer != null) {
                                targetPlayer.addAttachment(sourcePermanent.getId(), game);
                            } else {
                                return false;
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public CopyEnchantmentEffect copy() {
        return new CopyEnchantmentEffect(this);
    }
    
}

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
package mage.sets.iceage;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public class Seizures extends CardImpl {

    public Seizures(UUID ownerId) {
        super(ownerId, 47, "Seizures", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "ICE";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Whenever enchanted creature becomes tapped, Seizures deals 3 damage to that creature's controller unless that player pays {3}.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(new SeizuresEffect(), "enchanted creature"));
    }

    public Seizures(final Seizures card) {
        super(card);
    }

    @Override
    public Seizures copy() {
        return new Seizures(this);
    }
}


class SeizuresEffect extends OneShotEffect {

    public SeizuresEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 3 damage to that creature's controller unless that player pays {3}";
    }

    public SeizuresEffect(final SeizuresEffect effect) {
        super(effect);
    }

    @Override
    public SeizuresEffect copy() {
        return new SeizuresEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if(enchantment == null) {
            return false;
        }
        Permanent enchanted = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
        if(enchanted == null) {
            return false;
        }
        Player player = game.getPlayer(enchanted.getControllerId());
        if(player != null) {
            Cost cost = new ManaCostsImpl("{3}");
            if(cost.canPay(source, source.getSourceId(), player.getId(), game)
                && player.chooseUse(Outcome.Benefit, "Pay " + cost.getText() + " to avoid damage?", source, game)) {
                cost.clearPaid();
                if(cost.pay(source, game, source.getSourceId(), player.getId(), false)) {
                    return true;
                }
            }
            player.damage(3, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }
}

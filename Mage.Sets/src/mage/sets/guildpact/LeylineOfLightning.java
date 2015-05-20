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
package mage.sets.guildpact;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author emerald000
 */
public class LeylineOfLightning extends CardImpl {

    public LeylineOfLightning(UUID ownerId) {
        super(ownerId, 68, "Leyline of Lightning", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");
        this.expansionSetCode = "GPT";


        // If Leyline of Lightning is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());
        
        // Whenever you cast a spell, you may pay {1}. If you do, Leyline of Lightning deals 1 damage to target player.
        Ability ability = new SpellCastControllerTriggeredAbility(new LeylineOfLightningEffect(), true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public LeylineOfLightning(final LeylineOfLightning card) {
        super(card);
    }

    @Override
    public LeylineOfLightning copy() {
        return new LeylineOfLightning(this);
    }
}

class LeylineOfLightningEffect extends DamageTargetEffect {
    
    LeylineOfLightningEffect() {
        super(1);
        this.staticText = "you may pay {1}. If you do, {this} deals 1 damage to target player.";
    }
    
    LeylineOfLightningEffect(final LeylineOfLightningEffect effect) {
        super(effect);
    }
    
    @Override
    public LeylineOfLightningEffect copy() {
        return new LeylineOfLightningEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cost cost = new GenericManaCost(1);
            if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
                super.apply(game, source);
            }
            return true;
        }
        return false;
    }
}

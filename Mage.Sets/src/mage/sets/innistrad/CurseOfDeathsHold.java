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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward
 */
public class CurseOfDeathsHold extends CardImpl<CurseOfDeathsHold> {

    public CurseOfDeathsHold(UUID ownerId) {
        super(ownerId, 94, "Curse of Death's Hold", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Aura");
        this.subtype.add("Curse");

        this.color.setBlack(true);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        
        // Creatures enchanted player controls get -1/-1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CurseOfDeathsHoldEffect()));
    }

    public CurseOfDeathsHold(final CurseOfDeathsHold card) {
        super(card);
    }

    @Override
    public CurseOfDeathsHold copy() {
        return new CurseOfDeathsHold(this);
    }
}

class CurseOfDeathsHoldEffect extends ContinuousEffectImpl<CurseOfDeathsHoldEffect> {

    public CurseOfDeathsHoldEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.UnboostCreature);
        staticText = "Creatures enchanted player controls get -1/-1";
    }

    public CurseOfDeathsHoldEffect(final CurseOfDeathsHoldEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
		Permanent enchantment = game.getPermanent(source.getSourceId());
		if (enchantment != null && enchantment.getAttachedTo() != null) {
			Player player = game.getPlayer(enchantment.getAttachedTo());
			if (player != null) {
                for (Permanent perm: game.getBattlefield().getAllActivePermanents(FilterCreaturePermanent.getDefault(), player.getId())) {
                    perm.addPower(-1);
                    perm.addToughness(-1);
                }
                return true;
            }
        }
		return false;
    }

    @Override
    public CurseOfDeathsHoldEffect copy() {
        return new CurseOfDeathsHoldEffect(this);
    }

}
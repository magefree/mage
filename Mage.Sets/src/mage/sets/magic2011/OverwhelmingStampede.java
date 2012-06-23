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

package mage.sets.magic2011;

import mage.Constants.*;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class OverwhelmingStampede extends CardImpl<OverwhelmingStampede> {

    public OverwhelmingStampede(UUID ownerId) {
        super(ownerId, 189, "Overwhelming Stampede", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");
        this.expansionSetCode = "M11";
        this.color.setGreen(true);
        this.getSpellAbility().addEffect(new OverwhelmingStampedeEffect());
    }

    public OverwhelmingStampede(final OverwhelmingStampede card) {
        super(card);
    }

    @Override
    public OverwhelmingStampede copy() {
        return new OverwhelmingStampede(this);
    }
}

class OverwhelmingStampedeEffect extends ContinuousEffectImpl<OverwhelmingStampedeEffect> {

    public OverwhelmingStampedeEffect() {
        super(Duration.EndOfTurn, Outcome.AddAbility);
        staticText = "Until end of turn, creatures you control gain trample and get +X/+X, where X is the greatest power among creatures you control.";
    }

    public OverwhelmingStampedeEffect(final OverwhelmingStampedeEffect effect) {
        super(effect);
    }

    @Override
    public OverwhelmingStampedeEffect copy() {
        return new OverwhelmingStampedeEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        int maxPower = 0;
        for (Permanent perm: game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
            if (perm.getPower().getValue() > maxPower)
                maxPower = perm.getPower().getValue();
        }
        for (Permanent perm: game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game)) {
            switch (layer) {
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.ModifyPT_7c) {
                        perm.addPower(maxPower);
                        perm.addToughness(maxPower);
                    }
                    break;
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        perm.addAbility(TrampleAbility.getInstance(), game);
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.PTChangingEffects_7;
    }

}
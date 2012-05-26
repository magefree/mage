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

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 * @author nantuko
 */
public class BlasphemousAct extends CardImpl<BlasphemousAct> {

    public BlasphemousAct(UUID ownerId) {
        super(ownerId, 130, "Blasphemous Act", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{8}{R}");
        this.expansionSetCode = "ISD";

        this.color.setRed(true);

        // Blasphemous Act costs {1} less to cast for each creature on the battlefield.
        // Blasphemous Act deals 13 damage to each creature.
        // need to override DamageAllEffect because of rules string
        this.getSpellAbility().addEffect(new BlasphemousActEffect());
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int creatureCount = game.getState().getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), game).size();
        int cost = 8 - creatureCount;
        String adjustedCost = "{R}";
        if (cost > 0) {
            adjustedCost = "{" + String.valueOf(cost) + "}" + adjustedCost;
        }
        ability.getManaCostsToPay().clear();
        ability.getManaCostsToPay().load(adjustedCost);
    }

    public BlasphemousAct(final BlasphemousAct card) {
        super(card);
    }

    @Override
    public BlasphemousAct copy() {
        return new BlasphemousAct(this);
    }
}

class BlasphemousActEffect extends OneShotEffect<BlasphemousActEffect> {

    public BlasphemousActEffect() {
        super(Constants.Outcome.Damage);
        staticText = "{this} costs {1} less to cast for each creature on the battlefield.\n {this} deals 13 damage to each creature";
    }

    public BlasphemousActEffect(final BlasphemousActEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            permanent.damage(13, source.getId(), game, true, false);
        }
        return true;
    }

    @Override
    public BlasphemousActEffect copy() {
        return new BlasphemousActEffect(this);
    }

}

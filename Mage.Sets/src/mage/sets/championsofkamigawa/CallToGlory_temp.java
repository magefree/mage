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
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Loki
 */
public class CallToGlory extends CardImpl<CallToGlory> {


private final static FilterCreaturePermanent filter = new FilterCreaturePermanent("Samurai");

    static {
        filter.getSubtype().add("Samurai");
        filter.setScopeSubtype(Filter.ComparisonScope.Any);
    }

        public CallToGlory(UUID ownerId) {
        super(ownerId, 4, "Call to Glory", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "CHK";
        this.color.setWhite(true);
        this.getSpellAbility().addEffect(new CalltoGloryFirstEffect());
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 1, Constants.Duration.EndOfTurn, filter, false));
    }

    public CallToGlory(final CallToGlory card) {
        super(card);
    }

    @Override
    public CallToGlory copy() {
        return new CallToGlory(this);
    }

}


class CalltoGloryFirstEffect extends OneShotEffect<CalltoGloryFirstEffect> {

    public CalltoGloryFirstEffect() {
        super(Constants.Outcome.Untap);
    }

    public CalltoGloryFirstEffect(final CalltoGloryFirstEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(FilterCreaturePermanent.getDefault(), player.getId())) {
                creature.untap(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public CalltoGloryFirstEffect copy() {
        return new CalltoGloryFirstEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "Untap all creatures you control";
    }
}
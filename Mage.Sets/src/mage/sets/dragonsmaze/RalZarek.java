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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author LevelX2
 */
public class RalZarek extends CardImpl<RalZarek> {

    public RalZarek(UUID ownerId) {
        super(ownerId, 94, "Ral Zarek", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{R}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Ral");

        this.color.setRed(true);
        this.color.setBlue(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), false));

        // +1: Tap target permanent, then untap another target permanent.
        LoyaltyAbility ability1 = new LoyaltyAbility(new RalZarekTapUntapEffect(), 1);
        ability1.addTarget(new TargetPermanent(2, 2, new FilterPermanent(), false));
        this.addAbility(ability1);

        // -2: Ral Zarek deals 3 damage to target creature or player.
        LoyaltyAbility ability2 = new LoyaltyAbility(new DamageTargetEffect(3), -2);
        ability2.addTarget(new TargetCreatureOrPlayer(true));
        this.addAbility(ability2);

        // -7: Flip five coins. Take an extra turn after this one for each coin that comes up heads.
        this.addAbility(new LoyaltyAbility(new RalZarekExtraTurnsEffect(), -7));

    }

    public RalZarek(final RalZarek card) {
        super(card);
    }

    @Override
    public RalZarek copy() {
        return new RalZarek(this);
    }
}


class RalZarekTapUntapEffect extends OneShotEffect<RalZarekTapUntapEffect> {

    public RalZarekTapUntapEffect() {
        super(Outcome.Tap);
        this.staticText = "Tap target permanent, then untap another target permanent";
    }

    public RalZarekTapUntapEffect(final RalZarekTapUntapEffect effect) {
        super(effect);
    }

    @Override
    public RalZarekTapUntapEffect copy() {
        return new RalZarekTapUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int i = 0;
            for (UUID targetId : source.getTargets().get(0).getTargets()) {
                i++;
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    if (i==1) {
                        permanent.tap(game);
                    }
                    if (i==2) {
                        permanent.untap(game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class RalZarekExtraTurnsEffect extends OneShotEffect<RalZarekExtraTurnsEffect> {

    public RalZarekExtraTurnsEffect() {
        super(Outcome.ExtraTurn);
        this.staticText = "Flip five coins. Take an extra turn after this one for each coin that comes up heads";
    }

    public RalZarekExtraTurnsEffect(final RalZarekExtraTurnsEffect effect) {
        super(effect);
    }

    @Override
    public RalZarekExtraTurnsEffect copy() {
        return new RalZarekExtraTurnsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (int i=0; i<5; i++) {
                if (controller.flipCoin(game)) {
                    game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), false));
                }
            }
            return true;
        }
        return false;
    }
}

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
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.DemonToken;
import mage.players.Player;
import mage.game.command.emblems.ObNixilisOfTheBlackOathEmblem;

/**
 *
 * @author LevelX2
 */
public class ObNixilisOfTheBlackOath extends CardImpl {

    public ObNixilisOfTheBlackOath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{B}{B}");
        this.subtype.add("Nixilis");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(3));

        // +2: Each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new LoyaltyAbility(new ObNixilisOfTheBlackOathEffect1(), 2));

        // -2: Create a 5/5 black Demon creature token with flying. You lose 2 life.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new CreateTokenEffect(new DemonToken()), -2);
        loyaltyAbility.addEffect(new LoseLifeSourceControllerEffect(2));
        this.addAbility(loyaltyAbility);

        // -8: You get an emblem with "{1}{B}, Sacrifice a creature: You gain X life and draw X cards, where X is the sacrificed creature's power."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new ObNixilisOfTheBlackOathEmblem()), -8));

        // Ob Nixilis of the Black Oath can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    public ObNixilisOfTheBlackOath(final ObNixilisOfTheBlackOath card) {
        super(card);
    }

    @Override
    public ObNixilisOfTheBlackOath copy() {
        return new ObNixilisOfTheBlackOath(this);
    }
}

class ObNixilisOfTheBlackOathEffect1 extends OneShotEffect {

    public ObNixilisOfTheBlackOathEffect1() {
        super(Outcome.Damage);
        staticText = "Each opponent loses 1 life. You gain life equal to the life lost this way";
    }

    public ObNixilisOfTheBlackOathEffect1(final ObNixilisOfTheBlackOathEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int loseLife = 0;
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null) {
                    loseLife += opponent.loseLife(1, game, false);
                }
            }
            controller.gainLife(loseLife, game);
            return true;

        }
        return false;
    }

    @Override
    public ObNixilisOfTheBlackOathEffect1 copy() {
        return new ObNixilisOfTheBlackOathEffect1(this);
    }

}

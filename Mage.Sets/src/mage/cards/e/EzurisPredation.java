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
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BeastToken2;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class EzurisPredation extends CardImpl {

    public EzurisPredation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{G}{G}{G}");

        // For each creature your opponents control, create a 4/4 green Beast creature token. Each of those Beasts fights a different one of those creatures.
        this.getSpellAbility().addEffect(new EzurisPredationEffect());
    }

    public EzurisPredation(final EzurisPredation card) {
        super(card);
    }

    @Override
    public EzurisPredation copy() {
        return new EzurisPredation(this);
    }
}

class EzurisPredationEffect extends OneShotEffect {

    public EzurisPredationEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "For each creature your opponents control, create a 4/4 green Beast creature token. Each of those Beasts fights a different one of those creatures";
    }

    public EzurisPredationEffect(final EzurisPredationEffect effect) {
        super(effect);
    }

    @Override
    public EzurisPredationEffect copy() {
        return new EzurisPredationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        /*
         * Players can't cast spells or activate any abilities in between the
         * Beasts entering the battlefield and fighting the other creatures.
         * Ifthe Beasts entering the battlefield cause any abilities to trigger,
         * those abilities will be put onto the stack after Ezuri's Predation is
         * finished resolving.
         * You choose which Beast is fighting which creature
         * an opponent controls. Each of the "fights" happens at the same time.
         * If Ezuri's Predation creates more than one token for any given
         * creature (due to an effect such as the one Doubling Season creates),
         * the extra tokens won't fight any creature.
         */
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            FilterCreaturePermanent filterCreature = new FilterCreaturePermanent();
            filterCreature.add(new ControllerPredicate(TargetController.OPPONENT));
            List<Permanent> creaturesOfOpponents = game.getBattlefield().getActivePermanents(filterCreature, source.getControllerId(), source.getSourceId(), game);
            if (!creaturesOfOpponents.isEmpty()) {
                CreateTokenEffect effect = new CreateTokenEffect(new BeastToken2(), creaturesOfOpponents.size());
                effect.apply(game, source);
                for (UUID tokenId : effect.getLastAddedTokenIds()) {
                    Permanent token = game.getPermanent(tokenId);
                    if (token != null) {
                        if (creaturesOfOpponents.isEmpty()) {
                            break;
                        }
                        Permanent opponentCreature = creaturesOfOpponents.iterator().next();
                        creaturesOfOpponents.remove(opponentCreature);
                        token.fight(opponentCreature, source, game);
                        game.informPlayers(token.getLogName() + " fights " + opponentCreature.getLogName());
                    }
                }
            }
            return true;
        }
        return false;
    }
}

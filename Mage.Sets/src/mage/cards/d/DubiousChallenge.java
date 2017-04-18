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
package mage.cards.d;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class DubiousChallenge extends CardImpl {

    public DubiousChallenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Look at the top ten cards of your library, exile up to two creature cards from among them, then shuffle your library. Target opponent may choose one of the exiled cards and put it onto the battlefield under his or her control. Put the rest onto the battlefield under your control.
        getSpellAbility().addEffect(new DubiousChallengeEffect());
        getSpellAbility().addTarget(new TargetOpponent());
    }

    public DubiousChallenge(final DubiousChallenge card) {
        super(card);
    }

    @Override
    public DubiousChallenge copy() {
        return new DubiousChallenge(this);
    }
}

class DubiousChallengeEffect extends OneShotEffect {

    public DubiousChallengeEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top ten cards of your library, exile up to two creature cards from among them, then shuffle your library. Target opponent may choose one of the exiled cards and put it onto the battlefield under his or her control. Put the rest onto the battlefield under your control";
    }

    public DubiousChallengeEffect(final DubiousChallengeEffect effect) {
        super(effect);
    }

    @Override
    public DubiousChallengeEffect copy() {
        return new DubiousChallengeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Cards topCards = new CardsImpl();
            topCards.addAll(controller.getLibrary().getTopCards(game, 10));
            controller.lookAtCards(sourceObject.getIdName(), topCards, game);
            TargetCard targetCreatures = new TargetCard(0, 2, Zone.LIBRARY, new FilterCreatureCard());
            controller.choose(outcome, topCards, targetCreatures, game);
            Cards exiledCards = new CardsImpl(targetCreatures.getTargets());
            if (!exiledCards.isEmpty()) {
                controller.moveCards(exiledCards, Zone.EXILED, source, game);
                controller.shuffleLibrary(source, game);
                Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
                if (opponent != null) {
                    TargetCard targetOpponentCreature = new TargetCard(0, 1, Zone.EXILED, new FilterCreatureCard());
                    if (opponent.choose(outcome, exiledCards, targetOpponentCreature, game)) {
                        Card card = game.getCard(targetOpponentCreature.getFirstTarget());
                        if (card != null) {
                            opponent.moveCards(card, Zone.BATTLEFIELD, source, game);
                            exiledCards.remove(card);
                        }
                    }
                    if (!exiledCards.isEmpty()) {
                        controller.moveCards(exiledCards, Zone.BATTLEFIELD, source, game);
                    }
                }
            } else {
                controller.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}

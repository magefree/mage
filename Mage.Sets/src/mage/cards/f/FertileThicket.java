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
package mage.cards.f;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class FertileThicket extends CardImpl {

    public FertileThicket(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Fertile Thicket enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Fertile Thicket enters the battlefield, you may look at the top five cards of your library. If you do, reveal up to one basic land card from among them, then put that card on top of your library and the rest on the bottom in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new FertileThicketEffect(), true));

        // {T}: Add {G} to your mana pool.
        this.addAbility(new GreenManaAbility());
    }

    public FertileThicket(final FertileThicket card) {
        super(card);
    }

    @Override
    public FertileThicket copy() {
        return new FertileThicket(this);
    }
}

class FertileThicketEffect extends OneShotEffect {

    public FertileThicketEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may look at the top five cards of your library. If you do, reveal up to one basic land card from among them, then put that card on top of your library and the rest on the bottom in any order";
    }

    public FertileThicketEffect(final FertileThicketEffect effect) {
        super(effect);
    }

    @Override
    public FertileThicketEffect copy() {
        return new FertileThicketEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 5));
            controller.lookAtCards(sourceObject.getIdName(), cards, game);
            TargetCard target = new TargetCard(0, 1, Zone.LIBRARY, StaticFilters.FILTER_BASIC_LAND_CARD);
            controller.chooseTarget(outcome, cards, target, source, game);
            Cards cardsRevealed = new CardsImpl(target.getTargets());
            if (!cardsRevealed.isEmpty()) {
                controller.revealCards(sourceObject.getIdName(), cardsRevealed, game);
                cards.removeAll(cardsRevealed);
                controller.putCardsOnTopOfLibrary(cardsRevealed, game, source, true);
            }
            controller.putCardsOnBottomOfLibrary(cards, game, source, true);
            return true;
        }
        return false;
    }
}

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
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterBasicLandCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class OreskosExplorer extends CardImpl {

    public OreskosExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Oreskos Explorer enters the battlefield, search your library for up to X Plains cards,
        // where X is the number of players who control more lands than you. Reveal those cards, put them into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OreskosExplorerEffect()));

    }

    public OreskosExplorer(final OreskosExplorer card) {
        super(card);
    }

    @Override
    public OreskosExplorer copy() {
        return new OreskosExplorer(this);
    }
}

class OreskosExplorerEffect extends OneShotEffect {

    public OreskosExplorerEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "search your library for up to X Plains cards, where X is the number of players who control more lands than you. Reveal those cards, put them into your hand, then shuffle your library";
    }

    public OreskosExplorerEffect(final OreskosExplorerEffect effect) {
        super(effect);
    }

    @Override
    public OreskosExplorerEffect copy() {
        return new OreskosExplorerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        int controllerLands = game.getBattlefield().countAll(new FilterLandPermanent(), controller.getId(), game);
        int landsToSearch = 0;
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            if (!playerId.equals(controller.getId())) {
                if (controllerLands < game.getBattlefield().countAll(new FilterLandPermanent(), playerId, game)) {
                    landsToSearch++;
                }
            }
        }
        if (landsToSearch > 0) {
            FilterBasicLandCard filterPlains = new FilterBasicLandCard("up to " + landsToSearch + " Plains cards");
            filterPlains.add(new ControllerPredicate(TargetController.YOU));
            filterPlains.add(new SubtypePredicate(SubType.PLAINS));
            TargetCardInLibrary target = new TargetCardInLibrary(0, landsToSearch, filterPlains);
            if (controller.searchLibrary(target, game)) {
                Cards cards = new CardsImpl(target.getTargets());
                controller.revealCards(sourceObject.getIdName(), cards, game);
                controller.moveCards(cards.getCards(game), Zone.HAND, source, game);
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}

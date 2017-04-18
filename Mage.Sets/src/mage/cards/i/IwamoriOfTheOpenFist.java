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
package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class IwamoriOfTheOpenFist extends CardImpl {

    public IwamoriOfTheOpenFist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add("Human");
        this.subtype.add("Monk");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Iwamori of the Open Fist enters the battlefield, each opponent may put a legendary creature card from his or her hand onto the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new IwamoriOfTheOpenFistEffect(), false));
    }

    public IwamoriOfTheOpenFist(final IwamoriOfTheOpenFist card) {
        super(card);
    }

    @Override
    public IwamoriOfTheOpenFist copy() {
        return new IwamoriOfTheOpenFist(this);
    }
}

class IwamoriOfTheOpenFistEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("legendary creature card");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public IwamoriOfTheOpenFistEffect() {
        super(Outcome.Detriment);
        this.staticText = "each opponent may put a legendary creature card from his or her hand onto the battlefield";
    }

    public IwamoriOfTheOpenFistEffect(final IwamoriOfTheOpenFistEffect effect) {
        super(effect);
    }

    @Override
    public IwamoriOfTheOpenFistEffect copy() {
        return new IwamoriOfTheOpenFistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl();
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                Target target = new TargetCardInHand(filter);
                if (opponent != null && target.canChoose(source.getSourceId(), opponent.getId(), game)) {
                    if (opponent.chooseUse(Outcome.PutCreatureInPlay, "Put a legendary creature card from your hand onto the battlefield?", source, game)) {
                        if (target.chooseTarget(Outcome.PutCreatureInPlay, opponent.getId(), source, game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                cards.add(card);
                            }
                        }
                    }
                }
            }
            controller.moveCards(cards.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            return true;
        }

        return false;
    }
}

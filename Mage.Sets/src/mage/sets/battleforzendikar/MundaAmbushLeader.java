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
package mage.sets.battleforzendikar;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class MundaAmbushLeader extends CardImpl {

    public MundaAmbushLeader(UUID ownerId) {
        super(ownerId, 215, "Munda, Ambush Leader", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");
        this.expansionSetCode = "BFZ";
        this.supertype.add("Legendary");
        this.subtype.add("Kor");
        this.subtype.add("Ally");
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // <i>Rally</i>-Whenever Munda, Ambush Leader or another Ally enters the battlefield under your control, you may look at the top four cards of your library. If you do, reveal any number of Ally cards from among them, then put those cards on top of your library in any order and the rest on the bottom in any order.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new MundaAmbushLeaderEffect(), true));

    }

    public MundaAmbushLeader(final MundaAmbushLeader card) {
        super(card);
    }

    @Override
    public MundaAmbushLeader copy() {
        return new MundaAmbushLeader(this);
    }
}

class MundaAmbushLeaderEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("Ally cards");

    static {
        filter.add(new SubtypePredicate("Ally"));
    }

    public MundaAmbushLeaderEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may look at the top four cards of your library. If you do, reveal any number of Ally cards from among them, then put those cards on top of your library in any order and the rest on the bottom in any order";
    }

    public MundaAmbushLeaderEffect(final MundaAmbushLeaderEffect effect) {
        super(effect);
    }

    @Override
    public MundaAmbushLeaderEffect copy() {
        return new MundaAmbushLeaderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Cards allCards = new CardsImpl();
            allCards.addAll(controller.getLibrary().getTopCards(game, 4));
            controller.lookAtCards(sourceObject.getIdName(), allCards, game);
            Cards allyCards = new CardsImpl();
            allyCards.addAll(allCards.getCards(filter, source.getSourceId(), source.getControllerId(), game));
            if (!allyCards.isEmpty()) {
                controller.revealCards(sourceObject.getIdName(), allyCards, game, true);
                allCards.removeAll(allyCards);
                controller.putCardsOnTopOfLibrary(allyCards, game, source, true);
            }
            if (!allCards.isEmpty()) {
                controller.putCardsOnBottomOfLibrary(allCards, game, source, true);
            }
            return true;
        }
        return false;
    }
}

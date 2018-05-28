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
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleLibrarySourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author emerald000
 */
public final class AncestralKnowledge extends CardImpl {

    public AncestralKnowledge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // When Ancestral Knowledge enters the battlefield, look at the top ten cards of your library, then exile any number of them and put the rest back on top of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AncestralKnowledgeEffect()));

        // When Ancestral Knowledge leaves the battlefield, shuffle your library.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ShuffleLibrarySourceEffect(), false));
    }

    public AncestralKnowledge(final AncestralKnowledge card) {
        super(card);
    }

    @Override
    public AncestralKnowledge copy() {
        return new AncestralKnowledge(this);
    }
}

class AncestralKnowledgeEffect extends OneShotEffect {

    AncestralKnowledgeEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top ten cards of your library, then exile any number of them and put the rest back on top of your library in any order";
    }

    AncestralKnowledgeEffect(final AncestralKnowledgeEffect effect) {
        super(effect);
    }

    @Override
    public AncestralKnowledgeEffect copy() {
        return new AncestralKnowledgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 10));
            if (cards.size() > 0) {
                controller.lookAtCards(source, null, cards, game);
                TargetCard target = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, new FilterCard("cards to exile"));
                controller.choose(Outcome.Exile, cards, target, game);
                Cards toExile = new CardsImpl(target.getTargets());
                controller.moveCards(toExile, Zone.EXILED, source, game);
                cards.removeAll(toExile);
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
            }
            return true;
        }
        return false;
    }
}

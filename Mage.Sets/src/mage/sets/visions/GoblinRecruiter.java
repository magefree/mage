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
package mage.sets.visions;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
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
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Quercitron
 */
public class GoblinRecruiter extends CardImpl {

    public GoblinRecruiter(UUID ownerId) {
        super(ownerId, 80, "Goblin Recruiter", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "VIS";
        this.subtype.add("Goblin");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Goblin Recruiter enters the battlefield, search your library for any number of Goblin cards and reveal those cards. Shuffle your library, then put them on top of it in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GoblinRecruiterEffect(), false));
    }

    public GoblinRecruiter(final GoblinRecruiter card) {
        super(card);
    }

    @Override
    public GoblinRecruiter copy() {
        return new GoblinRecruiter(this);
    }
}

class GoblinRecruiterEffect extends OneShotEffect {

    private static final FilterCard goblinFilter = new FilterCard("Goblin cards");

    static {
        goblinFilter.add(new SubtypePredicate("Goblin"));
    }

    public GoblinRecruiterEffect() {
        super(Outcome.Benefit);
        this.staticText = "search your library for any number of Goblin cards and reveal those cards. Shuffle your library, then put them on top of it in any order.";
    }

    public GoblinRecruiterEffect(final GoblinRecruiterEffect effect) {
        super(effect);
    }

    @Override
    public GoblinRecruiterEffect copy() {
        return new GoblinRecruiterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInLibrary targetGoblins = new TargetCardInLibrary(0, Integer.MAX_VALUE, goblinFilter);
            Cards cards = new CardsImpl();
            if (controller.searchLibrary(targetGoblins, game)) {
                cards.addAll(targetGoblins.getTargets());
            }
            controller.revealCards(staticText, cards, game);
            controller.shuffleLibrary(game);

            int numberOfGoblins = cards.size();
            if (numberOfGoblins > 0) {
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
            }
            return true;
        }
        return false;
    }

}

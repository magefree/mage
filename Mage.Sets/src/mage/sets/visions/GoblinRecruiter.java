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
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
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
    
    private static final FilterCard putOnTopOfLibraryFilter = new FilterCard("card to put on the top of your library (last chosen will be on top)");
    
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
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetCardInLibrary targetGoblins = new TargetCardInLibrary(0, Integer.MAX_VALUE, goblinFilter);
            Cards cards = new CardsImpl();
            if (player.searchLibrary(targetGoblins, game)) {
                for (UUID targetId : targetGoblins.getTargets()) {
                    Card card = player.getLibrary().remove(targetId, game);
                    if (card != null) {
                        cards.add(card);
                    }
                }
            }
            
            player.shuffleLibrary(game);
               
            int numberOfGoblins = cards.size();
            if (numberOfGoblins > 0) {
                if (cards.size() > 1) {
                    TargetCard targetCard = new TargetCard(Zone.LIBRARY, putOnTopOfLibraryFilter);
                    while (player.isInGame() && cards.size() > 1) {
                        player.choose(Outcome.Benefit, cards, targetCard, game);
                        Card card = cards.get(targetCard.getFirstTarget(), game);
                        if (card != null) {
                            cards.remove(card);
                            card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                        }
                        targetCard.clearChosen();
                    }
                }
                if (cards.size() == 1) {
                    Card card = cards.get(cards.iterator().next(), game);
                    if (card != null) {
                        cards.remove(card);
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }
                }
            }

            game.informPlayers(new StringBuilder(player.getLogName()).append(" puts ")
                    .append(numberOfGoblins).append(" Goblin").append(numberOfGoblins == 1 ? " card" : " cards")
                    .append(" on top of his library").toString());

            return true;
        }
        return false;
    }
    
}

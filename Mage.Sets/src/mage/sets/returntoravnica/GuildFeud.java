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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;


/**
 *
 * @author LevelX2
 */
public class GuildFeud extends CardImpl<GuildFeud> {

    public GuildFeud(UUID ownerId) {
        super(ownerId, 97, "Guild Feud", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");
        this.expansionSetCode = "RTR";

        this.color.setRed(true);

        // At the beginning of your upkeep, target opponent reveals the top three cards
        // of his or her library, may put a creature card from among them onto the battlefield,
        // then puts the rest into his or her graveyard. You do the same with the top three
        // cards of your library. If two creatures are put onto the battlefield this way,
        // those creatures fight each other.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new GuildFeudEffect(), Constants.TargetController.YOU, true);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public GuildFeud(final GuildFeud card) {
        super(card);
    }

    @Override
    public GuildFeud copy() {
        return new GuildFeud(this);
    }
}


class GuildFeudEffect extends OneShotEffect<GuildFeudEffect> {

    public GuildFeudEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "target opponent reveals the top three cards of his or her library, may put a creature card from among them onto the battlefield, then puts the rest into his or her graveyard. You do the same with the top three cards of your library. If two creatures are put onto the battlefield this way, those creatures fight each other";
    }

    public GuildFeudEffect(final GuildFeudEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        Permanent opponentCreature = null;
        Permanent controllerCreature = null;
        if (opponent != null && controller != null) {
            for (int activePlayer = 0; activePlayer < 2; activePlayer++) {
                Player player = (activePlayer == 0? opponent : controller);
                Cards topThreeCards = new CardsImpl(Zone.PICK);
                for (int i = 0; i < 3; i++) {
                    if (player.getLibrary().size() > 0) {
                        Card topCard = player.getLibrary().removeFromTop(game);
                        if (topCard != null) {
                            topThreeCards.add(topCard);
                        }
                    }
                }
                player.revealCards(player.getName() + " top three library cards", topThreeCards, game);
                Card creatureToBattlefield;
                if (!topThreeCards.isEmpty()) {
                    if (player.chooseUse(Outcome.PutCreatureInPlay, "Put a creature card among them to the battlefield?", game)) {
                        TargetCard target = new TargetCard(Zone.PICK,
                                new FilterCreatureCard(
                                "creature card to put on the battlefield"));
                        if (player.choose(Outcome.PutCreatureInPlay, topThreeCards, target, game)) {
                            creatureToBattlefield = topThreeCards.get(target.getFirstTarget(), game);
                            if (creatureToBattlefield != null) {
                                topThreeCards.remove(creatureToBattlefield);
                                if (creatureToBattlefield.putOntoBattlefield(game, Zone.PICK,
                                        source.getSourceId(), player.getId())) {
                                    game.informPlayers("Guild Feud: " + player.getName() + " put " + creatureToBattlefield.getName() + " to the battlefield");
                                    if (activePlayer == 0) {
                                        opponentCreature = game.getPermanent(creatureToBattlefield.getId());
                                    } else {
                                        controllerCreature = game.getPermanent(creatureToBattlefield.getId());
                                    }
                                }
                            }
                        }
                    }

                    if (topThreeCards.size() > 0) {

                        while (topThreeCards.size() > 0) {
                            Card card = topThreeCards.get(topThreeCards.iterator().next(), game);
                            topThreeCards.remove(card);
                            card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, true);
                        }
                    }
                }
            }
            // If two creatures are put onto the battlefield this way, those creatures fight each other
            if (opponentCreature != null && controllerCreature != null) {
                int power = opponentCreature.getPower().getValue();
                opponentCreature.damage(controllerCreature.getPower().getValue(), source.getSourceId(), game, true, false);
                controllerCreature.damage(power, source.getSourceId(), game, true, false);
            }
        }
        return false;
    }

    @Override
    public GuildFeudEffect copy() {
        return new GuildFeudEffect(this);
    }
}
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
package mage.sets.magic2014;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class JacesMindseeker extends CardImpl {

    public JacesMindseeker(UUID ownerId) {
        super(ownerId, 61, "Jace's Mindseeker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.expansionSetCode = "M14";
        this.subtype.add("Fish");
        this.subtype.add("Illusion");

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Jace's Mindseeker enters the battlefield, target opponent puts the top five cards of his or her library into his or her graveyard.
        // You may cast an instant or sorcery card from among them without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new JaceMindseekerEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public JacesMindseeker(final JacesMindseeker card) {
        super(card);
    }

    @Override
    public JacesMindseeker copy() {
        return new JacesMindseeker(this);
    }
}

class JaceMindseekerEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterInstantOrSorceryCard();

    public JaceMindseekerEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "target opponent puts the top five cards of his or her library into his or her graveyard. You may cast an instant or sorcery card from among them without paying its mana cost";
    }

    public JaceMindseekerEffect(final JaceMindseekerEffect effect) {
        super(effect);
    }

    @Override
    public JaceMindseekerEffect copy() {
        return new JaceMindseekerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cardsToCast = new CardsImpl();
        Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetOpponent != null) {
            Set<Card> allCards = targetOpponent.getLibrary().getTopCards(game, 5);
            targetOpponent.moveCards(allCards, Zone.LIBRARY, Zone.GRAVEYARD, source, game);
            for (Card card : allCards) {
                if (filter.match(card, game)) {
                    Zone zone = game.getState().getZone(card.getId());
                    // If the five cards are put into a public zone such as exile instead of a graveyard (perhaps due to the ability of Rest in Peace),
                    // you can cast one of those instant or sorcery cards from that zone.
                    if (zone.equals(Zone.GRAVEYARD) || zone.equals(Zone.EXILED)) {
                        cardsToCast.add(card);
                    }
                }
            }
        }

        // cast an instant or sorcery for free
        if (cardsToCast.size() > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                TargetCard target = new TargetCard(Zone.GRAVEYARD, filter); // zone should be ignored here
                target.setNotTarget(true);
                if (controller.chooseUse(outcome, "Cast an instant or sorcery card from among them for free?", source, game)
                        && controller.choose(outcome, cardsToCast, target, game)) {
                    Card card = cardsToCast.get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.cast(card.getSpellAbility(), game, true);
                    }
                }
            }

        }

        return false;
    }
}

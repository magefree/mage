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
public class JacesMindseeker extends CardImpl<JacesMindseeker> {

    public JacesMindseeker(UUID ownerId) {
        super(ownerId, 61, "Jace's Mindseeker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.expansionSetCode = "M14";
        this.subtype.add("Fish");
        this.subtype.add("Illusion");

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Jace's Mindseeker enters the battlefield, target opponent puts the top five cards of his or her library into his or her graveyard. 
        // You may cast an instant or sorcery card from among them without paying its mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new JaceMindseekerEffect());
        ability.addTarget(new TargetOpponent(true));
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

class JaceMindseekerEffect extends OneShotEffect<JaceMindseekerEffect> {

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
        Cards cards = new CardsImpl();
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            // putting cards to grave shouldn't end the game, so getting minimun available
            int cardsCount = Math.min(5, player.getLibrary().size());
            for (int i = 0; i < cardsCount; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                    if (filter.match(card, game)) {
                        cards.add(card);
                    }
                }
                else {
                    throw new IllegalArgumentException("couldn't get card from library");
                }
            }
        }

        // cast an instant or sorcery for free
        // TODO: Check if card can also be cast if it doesn't end in the graveyard due to other active effects (LevelX2 08.07.2013).
        if (cards.size() > 0) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                TargetCard target = new TargetCard(Zone.PICK, filter);
                target.setRequired(true);
                target.setNotTarget(true);
                if (controller.chooseUse(outcome, "Cast an instant or sorcery card from among them for free?", game)
                        && controller.choose(outcome, cards, target, game)) {                  
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.cast(card.getSpellAbility(), game, true);
                    }
                }
            }
         
        }

        return false;
    }
}

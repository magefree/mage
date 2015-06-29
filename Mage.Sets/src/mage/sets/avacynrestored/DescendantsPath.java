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
package mage.sets.avacynrestored;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author noxx

 */
public class DescendantsPath extends CardImpl {

    public DescendantsPath(UUID ownerId) {
        super(ownerId, 173, "Descendants' Path", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.expansionSetCode = "AVR";

        // At the beginning of your upkeep, reveal the top card of your library. If it's a creature card that shares a creature type with a creature you control, you may cast that card without paying its mana cost. Otherwise, put that card on the bottom of your library.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DescendantsPathEffect(), TargetController.YOU, false);
        this.addAbility(ability);
    }

    public DescendantsPath(final DescendantsPath card) {
        super(card);
    }

    @Override
    public DescendantsPath copy() {
        return new DescendantsPath(this);
    }
}

class DescendantsPathEffect extends OneShotEffect {

    public DescendantsPathEffect() {
        super(Outcome.Discard);
        this.staticText = "reveal the top card of your library. If it's a creature card that shares a creature type with a creature you control, you may cast that card without paying its mana cost. Otherwise, put that card on the bottom of your library";
    }

    public DescendantsPathEffect(final DescendantsPathEffect effect) {
        super(effect);
    }

    @Override
    public DescendantsPathEffect copy() {
        return new DescendantsPathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (controller.getLibrary().size() > 0) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card == null) {
                    return false;
                }
                controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
                if (card.getCardType().contains(CardType.CREATURE)) {                    
                    FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
                    boolean found = false;
                    for (Permanent  permanent: game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
                        if (CardUtil.shareSubtypes(card, permanent)) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        game.informPlayers(sourceObject.getLogName() + ": Found a creature that shares a creature type with the revealed card.");
                        if (controller.chooseUse(Outcome.Benefit, "Cast the card?", source, game)) {
                            controller.cast(card.getSpellAbility(), game, true);
                        } else {
                            game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " canceled casting the card.");
                            controller.getLibrary().putOnBottom(card, game);
                        }
                    } else {
                        game.informPlayers(sourceObject.getLogName() + ": No creature that shares a creature type with the revealed card.");
                        controller.getLibrary().putOnBottom(card, game);
                    }
                } else {
                    game.informPlayers(sourceObject.getLogName() + ": Put " + card.getLogName() + " on the bottom.");
                    controller.getLibrary().putOnBottom(card, game);
                }

                return true;
            }
        }
        return false;
    }
}


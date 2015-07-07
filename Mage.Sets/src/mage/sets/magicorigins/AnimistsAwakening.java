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
package mage.sets.magicorigins;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AnimistsAwakening extends CardImpl {

    public AnimistsAwakening(UUID ownerId) {
        super(ownerId, 169, "Animist's Awakening", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{X}{G}");
        this.expansionSetCode = "ORI";

        // Reveal the top X cards of your library. Put all land cards from among them onto the battlefield tapped and the rest on the bottom of your library in any order.
        // <i>Spell mastery</i> — If there are two or more instant and/or sorcery cards in your graveyard, untap those lands.
        this.getSpellAbility().addEffect(new AnimistsAwakeningEffect());
    }

    public AnimistsAwakening(final AnimistsAwakening card) {
        super(card);
    }

    @Override
    public AnimistsAwakening copy() {
        return new AnimistsAwakening(this);
    }
}

class AnimistsAwakeningEffect extends OneShotEffect {

    public AnimistsAwakeningEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Reveal the top X cards of your library. Put all land cards from among them onto the battlefield tapped and the rest on the bottom of your library in any order."
                + "<br><i>Spell mastery</i> — If there are two or more instant and/or sorcery cards in your graveyard, untap those lands";
    }

    public AnimistsAwakeningEffect(final AnimistsAwakeningEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(Zone.LIBRARY);
        int xValue = source.getManaCostsToPay().getX();
        cards.addAll(controller.getLibrary().getTopCards(game, xValue));
        List<Permanent> lands = new ArrayList<>();
        if (cards.size() > 0) {
            controller.revealCards(sourceObject.getIdName(), cards, game);
            for (Card card : cards.getCards(new FilterLandCard(), source.getSourceId(), source.getControllerId(), game)) {
                cards.remove(card);
                controller.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId(), true);
                Permanent land = game.getPermanent(card.getId());
                if (land != null) {
                    lands.add(land);
                }

            }
            controller.putCardsOnBottomOfLibrary(cards, game, source, true);

            if (SpellMasteryCondition.getInstance().apply(game, source)) {
                for (Permanent land : lands) {
                    land.untap(game);
                }
            }
        }
        return true;
    }

    @Override
    public AnimistsAwakeningEffect copy() {
        return new AnimistsAwakeningEffect(this);
    }

}

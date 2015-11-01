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

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class GatherThePack extends CardImpl {

    public GatherThePack(UUID ownerId) {
        super(ownerId, 178, "Gather the Pack", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{G}");
        this.expansionSetCode = "ORI";

        // Reveal the top five cards of your library. You may put a creature card from among them into your hand. Put the rest into your graveyard.
        // <i>Spell mastery</i> — If there are two or more instant and/or sorcery cards in your graveyard, put up to two creature cards from among the revealed cards into your hand instead of one.
        this.getSpellAbility().addEffect(new GatherThePackEffect());
    }

    public GatherThePack(final GatherThePack card) {
        super(card);
    }

    @Override
    public GatherThePack copy() {
        return new GatherThePack(this);
    }
}

class GatherThePackEffect extends OneShotEffect {

    public GatherThePackEffect(final GatherThePackEffect effect) {
        super(effect);
    }

    public GatherThePackEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Reveal the top five cards of your library. You may put a creature card from among them into your hand. Put the rest into your graveyard."
                + "<br><i>Spell mastery</i> — If there are two or more instant and/or sorcery cards in your graveyard, put up to two creature cards from among the revealed cards into your hand instead of one";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(Zone.LIBRARY);
        cards.addAll(controller.getLibrary().getTopCards(game, 5));
        if (!cards.isEmpty()) {
            controller.revealCards(sourceObject.getIdName(), cards, game);
            int creatures = cards.count(new FilterCreatureCard(), source.getSourceId(), source.getControllerId(), game);
            if (creatures > 0) {
                int max = 1;
                if (SpellMasteryCondition.getInstance().apply(game, source) && creatures > 1) {
                    max++;
                }
                TargetCard target = new TargetCard(0, max, Zone.LIBRARY, new FilterCreatureCard("creature card" + (max > 1 ? "s" : "") + " to put into your hand"));
                if (controller.choose(Outcome.PutCreatureInPlay, cards, target, game)) {
                    Cards cardsToHand = new CardsImpl(target.getTargets());
                    if (cardsToHand.size() > 0) {
                        cards.removeAll(cardsToHand);
                        controller.moveCards(cardsToHand, Zone.LIBRARY, Zone.HAND, source, game);
                    }
                }
            }
            if (cards.size() > 0) {
                controller.moveCards(cards, Zone.LIBRARY, Zone.GRAVEYARD, source, game);
            }
        }
        return true;
    }

    @Override
    public GatherThePackEffect copy() {
        return new GatherThePackEffect(this);
    }
}

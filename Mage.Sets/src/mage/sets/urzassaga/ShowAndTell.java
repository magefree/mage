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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author North
 */
public class ShowAndTell extends CardImpl<ShowAndTell> {

    public ShowAndTell(UUID ownerId) {
        super(ownerId, 96, "Show and Tell", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{U}");
        this.expansionSetCode = "USG";

        this.color.setBlue(true);

        // Each player may put an artifact, creature, enchantment, or land card from his or her hand onto the battlefield.
        this.getSpellAbility().addEffect(new ShowAndTellEffect());
    }

    public ShowAndTell(final ShowAndTell card) {
        super(card);
    }

    @Override
    public ShowAndTell copy() {
        return new ShowAndTell(this);
    }
}

class ShowAndTellEffect extends OneShotEffect<ShowAndTellEffect> {

    private static final FilterCard filter = new FilterCard("artifact, creature, enchantment, or land card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.LAND)));
    }

    public ShowAndTellEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Each player may put an artifact, creature, enchantment, or land card from his or her hand onto the battlefield";
    }

    public ShowAndTellEffect(final ShowAndTellEffect effect) {
        super(effect);
    }

    @Override
    public ShowAndTellEffect copy() {
        return new ShowAndTellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        for (UUID playerId : controller.getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                TargetCardInHand target = new TargetCardInHand(filter);
                if (player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        card.putOntoBattlefield(game, Zone.HAND, source.getId(), player.getId());
                    }
                }
            }
        }
        return true;
    }
}

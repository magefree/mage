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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author North
 */
public class AppetiteForBrains extends CardImpl<AppetiteForBrains> {

    public AppetiteForBrains(UUID ownerId) {
        super(ownerId, 84, "Appetite for Brains", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{B}");
        this.expansionSetCode = "AVR";

        this.color.setBlack(true);

        // Target opponent reveals his or her hand. You choose a card from it with converted mana cost 4 or greater and exile that card.
        this.getSpellAbility().addEffect(new AppetiteForBrainsEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public AppetiteForBrains(final AppetiteForBrains card) {
        super(card);
    }

    @Override
    public AppetiteForBrains copy() {
        return new AppetiteForBrains(this);
    }
}

class AppetiteForBrainsEffect extends OneShotEffect<AppetiteForBrainsEffect> {

    public AppetiteForBrainsEffect() {
        super(Outcome.Exile);
        this.staticText = "Target opponent reveals his or her hand. You choose a card from it with converted mana cost 4 or greater and exile that card";
    }

    public AppetiteForBrainsEffect(final AppetiteForBrainsEffect effect) {
        super(effect);
    }

    @Override
    public AppetiteForBrainsEffect copy() {
        return new AppetiteForBrainsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (player != null && targetPlayer != null) {
            FilterCard filter = new FilterCard("with converted mana cost 4 or greater");
            filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.GreaterThan, 3));

            targetPlayer.revealCards("Appetite for Brains", targetPlayer.getHand(), game);
            TargetCard target = new TargetCard(Zone.PICK, filter);
            target.setRequired(true);
            if (targetPlayer.getHand().count(filter, game) > 0
                    && player.choose(Outcome.Exile, targetPlayer.getHand(), target, game)) {
                Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    return card.moveToExile(null, "", source.getId(), game);
                }
            }
        }
        return false;
    }
}

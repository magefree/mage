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
package mage.sets.zendikar;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.mana.KickerManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public class SadisticSacrament extends CardImpl<SadisticSacrament> {

    private static final String ruleText = "Search target player's library for up to three cards, exile them, then that player shuffles his or her library. If {this} was kicked, instead search that player's library for up to fifteen cards, exile them, then that player shuffles his or her library";

    public SadisticSacrament(UUID ownerId) {
        super(ownerId, 110, "Sadistic Sacrament", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{B}{B}{B}");
        this.expansionSetCode = "ZEN";

        this.color.setBlack(true);

        // Kicker {7}
        this.addAbility(new KickerAbility(new KickerManaCost("{7}")));

        // Search target player's library for up to three cards, exile them, then that player shuffles his or her library.
        // If Sadistic Sacrament was kicked, instead search that player's library for up to fifteen cards, exile them, then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SadisticSacramentEffect(15),
                new SadisticSacramentEffect(3),
                KickedCondition.getInstance(),
                ruleText));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public SadisticSacrament(final SadisticSacrament card) {
        super(card);
    }

    @Override
    public SadisticSacrament copy() {
        return new SadisticSacrament(this);
    }
}

class SadisticSacramentEffect extends OneShotEffect<SadisticSacramentEffect> {

    private int amount;

    public SadisticSacramentEffect(int amount) {
        super(Outcome.Exile);
        this.amount = amount;
    }

    public SadisticSacramentEffect(final SadisticSacramentEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public SadisticSacramentEffect copy() {
        return new SadisticSacramentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && targetPlayer != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, amount, new FilterCard("cards to exile"));
            if (player.searchLibrary(target, game, targetPlayer.getId())) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = targetPlayer.getLibrary().remove(targetId, game);
                    if (card != null) {
                        card.moveToExile(null, "", source.getId(), game);
                    }
                }
            }
            targetPlayer.shuffleLibrary(game);
            return true;
        }

        return false;
    }
}

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
package mage.sets.saviorsofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.EpicEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 *
 */
public class EternalDominion extends CardImpl<EternalDominion> {

    public EternalDominion(UUID ownerId) {
        super(ownerId, 36, "Eternal Dominion", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{7}{U}{U}{U}");
        this.expansionSetCode = "SOK";

        this.color.setBlue(true);

        // Search target opponent's library for an artifact, creature, enchantment, or land card.
        // Put that card onto the battlefield under your control. Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new EternalDominionEffect());
        this.getSpellAbility().addTarget(new TargetOpponent(true));
        
        // Epic
        this.getSpellAbility().addEffect(new EpicEffect());

    }

    public EternalDominion(final EternalDominion card) {
        super(card);
    }

    @Override
    public EternalDominion copy() {
        return new EternalDominion(this);
    }
}

class EternalDominionEffect extends OneShotEffect<EternalDominionEffect> {
    
    private static final FilterCard filter = new FilterCard();
    
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT),
                new CardTypePredicate(CardType.LAND)));
    }

    public EternalDominionEffect() {
        super(Outcome.Benefit);
        staticText = "Search target opponent's library for an artifact, creature, enchantment, or land card. Put that card onto the battlefield under your control. Then that player shuffles his or her library";
    }

    public EternalDominionEffect(final EternalDominionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        Player opponent = game.getPlayer(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (opponent != null
                && you != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            you.searchLibrary(target, game, opponent.getId());
            Card targetCard = game.getCard(target.getFirstTarget());
            if (targetCard != null) {
                applied = you.putOntoBattlefieldWithInfo(targetCard, game, Zone.LIBRARY, source.getSourceId());
            }
            opponent.shuffleLibrary(game);
        }
        return applied;
    }

    @Override
    public EternalDominionEffect copy() {
        return new EternalDominionEffect(this);
    }
}

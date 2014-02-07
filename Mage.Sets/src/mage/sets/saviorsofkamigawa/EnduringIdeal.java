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
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth

 */
public class EnduringIdeal extends CardImpl<EnduringIdeal> {

    public EnduringIdeal(UUID ownerId) {
        super(ownerId, 9, "Enduring Ideal", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{W}");
        this.expansionSetCode = "SOK";

        this.color.setWhite(true);

        // Search your library for an enchantment card and put it onto the battlefield. Then shuffle your library.
        this.getSpellAbility().addEffect(new EnduringIdealEffect());
        
        // Epic
        this.getSpellAbility().addEffect(new EpicEffect());
        
    }

    public EnduringIdeal(final EnduringIdeal card) {
        super(card);
    }

    @Override
    public EnduringIdeal copy() {
        return new EnduringIdeal(this);
    }
}

class EnduringIdealEffect extends OneShotEffect<EnduringIdealEffect> {
    
    private static final FilterCard filter = new FilterCard();
    
    static {
        filter.add(new CardTypePredicate(CardType.ENCHANTMENT));
    }

    public EnduringIdealEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for an enchantment card and put it onto the battlefield. Then shuffle your library";
    }

    public EnduringIdealEffect(final EnduringIdealEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            you.searchLibrary(target, game);
            Card targetCard = game.getCard(target.getFirstTarget());
            if (targetCard != null) {
                applied = targetCard.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), you.getId());
            }
            you.shuffleLibrary(game);
        }
        return applied;
    }

    @Override
    public EnduringIdealEffect copy() {
        return new EnduringIdealEffect(this);
    }
}
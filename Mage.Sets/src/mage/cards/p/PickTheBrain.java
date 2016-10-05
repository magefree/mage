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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class PickTheBrain extends CardImpl {
    
    public PickTheBrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // Target opponent reveals his or her hand. You choose a nonland card from it and exile that card.
        // <i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, search that player's graveyard, hand, and library for any number of cards with the same name as the exiled card, exile those cards, then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new PickTheBrainEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public PickTheBrain(final PickTheBrain card) {
        super(card);
    }

    @Override
    public PickTheBrain copy() {
        return new PickTheBrain(this);
    }
}

class PickTheBrainEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    private static final FilterCard filter = new FilterCard("a nonland card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }
    
    public PickTheBrainEffect() {
        super(true, "that card's controller", "all cards with the same name as that card");
    }

    public PickTheBrainEffect(final PickTheBrainEffect effect) {
        super(effect);
    }

    @Override
    public PickTheBrainEffect copy() {
        return new PickTheBrainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent != null && controller != null) {            
            if (!opponent.getHand().isEmpty()) {
                opponent.revealCards("Exile " + filter.getMessage(), opponent.getHand(), game);
                TargetCard target = new TargetCard(Zone.HAND, filter);
                if (controller.choose(Outcome.Exile, opponent.getHand(), target, game)) {
                    Card card = opponent.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.HAND, true);
                        
                        // Check the Delirium condition
                        if (!DeliriumCondition.getInstance().apply(game, source)) {
                            return true;
                        }
                        return this.applySearchAndExile(game, source, card.getName(), opponent.getId());   
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Target opponent reveals his or her hand. You choose a nonland card from it and exile that card.<br><br>"
                + "<i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, "
                + "search that player's graveyard, hand, and library for any number of cards "
                + "with the same name as the exiled card, exile those cards, then that player shuffles his or her library";
    }
}
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
package mage.sets.battleforzendikar;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class TitansPresence extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a colorless creature card from your hand");

    static {
        filter.add(new ColorlessPredicate());
    }

    public TitansPresence(UUID ownerId) {
        super(ownerId, 14, "Titan's Presence", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}");
        this.expansionSetCode = "BFZ";

        // As an additional cost to cast Titan's Presence, reveal a colorless creature card from your hand.
        this.getSpellAbility().addCost(new RevealTargetFromHandCost(new TargetCardInHand(filter)));

        // Exile target creature if its power is less than or equal to the revealed card's power.
        this.getSpellAbility().addEffect(new TitansPresenceEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public TitansPresence(final TitansPresence card) {
        super(card);
    }

    @Override
    public TitansPresence copy() {
        return new TitansPresence(this);
    }
}

class TitansPresenceEffect extends OneShotEffect {

    public TitansPresenceEffect() {
        super(Outcome.Exile);
        staticText = "Exile target creature if its power is less than or equal to the revealed card's power";
    }

    public TitansPresenceEffect(TitansPresenceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = (RevealTargetFromHandCost) source.getCosts().get(0);
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (cost != null && creature != null && controller != null) {
            List<Card> revealedCards = cost.getRevealedCards();
            if (!revealedCards.isEmpty()) {
                Card card = revealedCards.iterator().next();
                if (card != null && card.getPower().getValue() >= creature.getPower().getValue()) {
                    controller.moveCards(creature, null, Zone.EXILED, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public TitansPresenceEffect copy() {
        return new TitansPresenceEffect(this);
    }

}

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
package mage.cards.m;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class MindSwords extends CardImpl {

    private static final FilterLandPermanent filterSwamp = new FilterLandPermanent("If you control a Swamp");

    static {
        filterSwamp.add(new SubtypePredicate(SubType.SWAMP));
    }

    public MindSwords(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // If you control a Swamp, you may sacrifice a creature rather than pay Mind Swords's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)),
                new PermanentsOnTheBattlefieldCondition(filterSwamp), null
        ));

        // Each player exiles two cards from their hand.
        this.getSpellAbility().addEffect(new MindSwordsEffect());
    }

    public MindSwords(final MindSwords card) {
        super(card);
    }

    @Override
    public MindSwords copy() {
        return new MindSwords(this);
    }
}

class MindSwordsEffect extends OneShotEffect {

    MindSwordsEffect() {
        super(Outcome.Exile);
        this.staticText = "Each player exiles two cards from their hand.";
    }

    MindSwordsEffect(final MindSwordsEffect effect) {
        super(effect);
    }

    @Override
    public MindSwordsEffect copy() {
        return new MindSwordsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Store for each player the cards to exile, that's important because all exile shall happen at the same time
        Map<UUID, Cards> cardsToExile = new HashMap<>();
        // Each player chooses 2 cards to discard
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            int numberOfCardsToExile = Math.min(2, player.getHand().size());
            Cards cards = new CardsImpl();

            Target target = new TargetCardInHand(numberOfCardsToExile, new FilterCard());

            player.chooseTarget(Outcome.Exile, target, source, game);
            cards.addAll(target.getTargets());
            cardsToExile.put(playerId, cards);
        }
        // Exile all choosen cards
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            Cards cardsPlayerChoseToExile = cardsToExile.get(playerId);
            if (cardsPlayerChoseToExile == null) {
                continue;
            }
            player.moveCards(cardsPlayerChoseToExile.getCards(game), Zone.EXILED, source, game);
        }
        return true;
    }
}

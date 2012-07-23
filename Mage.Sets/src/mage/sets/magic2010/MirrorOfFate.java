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
package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public class MirrorOfFate extends CardImpl<MirrorOfFate> {

    public MirrorOfFate(UUID ownerId) {
        super(ownerId, 215, "Mirror of Fate", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "M10";

        // {tap}, Sacrifice Mirror of Fate: Choose up to seven face-up exiled cards you own. Exile all the cards from your library, then put the chosen cards on top of your library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new MirrorOfFateEffect(),
                new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public MirrorOfFate(final MirrorOfFate card) {
        super(card);
    }

    @Override
    public MirrorOfFate copy() {
        return new MirrorOfFate(this);
    }
}

class MirrorOfFateEffect extends OneShotEffect<MirrorOfFateEffect> {

    public MirrorOfFateEffect() {
        super(Outcome.Neutral);
        this.staticText = "Choose up to seven face-up exiled cards you own. Exile all the cards from your library, then put the chosen cards on top of your library";
    }

    public MirrorOfFateEffect(final MirrorOfFateEffect effect) {
        super(effect);
    }

    @Override
    public MirrorOfFateEffect copy() {
        return new MirrorOfFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        CardsImpl cards = new CardsImpl();
        MirrorOfFateTarget targetExile = new MirrorOfFateTarget();
        if (player.choose(outcome, targetExile, source.getSourceId(), game)) {
            for (UUID cardId : targetExile.getTargets()) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    cards.add(card);
                }
            }
        }

        for (Card card : player.getLibrary().getCards(game)) {
            card.moveToExile(null, null, source.getSourceId(), game);
        }

        TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on top of your library"));
        target.setRequired(true);
        while (cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
            }
            target.clearChosen();
        }
        if (cards.size() == 1) {
            Card card = cards.get(cards.iterator().next(), game);
            card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
        }

        return true;
    }
}

class FaceUpPredicate implements Predicate<Card> {

    @Override
    public boolean apply(Card input, Game game) {
        return !input.isFaceDown();
    }

    @Override
    public String toString() {
        return "FaceUp";
    }
}

class MirrorOfFateTarget extends TargetCard<MirrorOfFateTarget> {

    public MirrorOfFateTarget() {
        super(0, 7, Zone.EXILED, new FilterCard());
        filter.add(new FaceUpPredicate());
        this.targetName = "face-up exiled cards you own";
    }

    public MirrorOfFateTarget(final MirrorOfFateTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && card.getOwnerId().equals(source.getControllerId())
                && game.getState().getZone(card.getId()) == Zone.EXILED) {
            for (ExileZone exile : game.getExile().getExileZones()) {
                if (exile != null && exile.contains(id)) {
                    return filter.match(card, source.getControllerId(), game);
                }
            }
        }
        return false;
    }

    @Override
    public MirrorOfFateTarget copy() {
        return new MirrorOfFateTarget(this);
    }
}

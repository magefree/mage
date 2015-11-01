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

import java.util.Set;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetDiscard;

/**
 *
 * @author North
 */
public class ChandraAblaze extends CardImpl {

    public ChandraAblaze(UUID ownerId) {
        super(ownerId, 120, "Chandra Ablaze", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{4}{R}{R}");
        this.expansionSetCode = "ZEN";
        this.subtype.add("Chandra");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(5));

        // +1: Discard a card. If a red card is discarded this way, Chandra Ablaze deals 4 damage to target creature or player.
        LoyaltyAbility ability = new LoyaltyAbility(new ChandraAblazeEffect1(), 1);
        ability.addEffect(new ChandraAblazeEffect2());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
        // -2: Each player discards his or her hand, then draws three cards.
        ability = new LoyaltyAbility(new DiscardHandAllEffect(), -2);
        Effect effect = new DrawCardAllEffect(3);
        effect.setText(", then draws three cards");
        ability.addEffect(effect);
        this.addAbility(ability);
        // -7: Cast any number of red instant and/or sorcery cards from your graveyard without paying their mana costs.
        ability = new LoyaltyAbility(new ChandraAblazeEffect5(), -7);
        this.addAbility(ability);
    }

    public ChandraAblaze(final ChandraAblaze card) {
        super(card);
    }

    @Override
    public ChandraAblaze copy() {
        return new ChandraAblaze(this);
    }
}

class ChandraAblazeEffect1 extends OneShotEffect {

    public ChandraAblazeEffect1() {
        super(Outcome.Discard);
        this.staticText = "Discard a card";
    }

    public ChandraAblazeEffect1(final ChandraAblazeEffect1 effect) {
        super(effect);
    }

    @Override
    public ChandraAblazeEffect1 copy() {
        return new ChandraAblazeEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            TargetDiscard target = new TargetDiscard(player.getId());
            player.choose(Outcome.Discard, target, source.getSourceId(), game);
            Card card = player.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                player.discard(card, source, game);
                source.getEffects().get(1).setValue("discardedCard", card);
                game.getState().setValue(source.getSourceId().toString(), card);
                return true;
            }
        }
        return false;
    }
}

class ChandraAblazeEffect2 extends OneShotEffect {

    public ChandraAblazeEffect2() {
        super(Outcome.Damage);
        this.staticText = "If a red card is discarded this way, {this} deals 4 damage to target creature or player";
    }

    public ChandraAblazeEffect2(final ChandraAblazeEffect2 effect) {
        super(effect);
    }

    @Override
    public ChandraAblazeEffect2 copy() {
        return new ChandraAblazeEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = (Card) this.getValue("discardedCard");
        if (card != null && card.getColor(game).isRed()) {
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null) {
                permanent.damage(4, source.getSourceId(), game, false, true);
                return true;
            }

            Player player = game.getPlayer(targetPointer.getFirst(game, source));
            if (player != null) {
                player.damage(4, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}

class ChandraAblazeEffect5 extends OneShotEffect {

    public ChandraAblazeEffect5() {
        super(Outcome.PlayForFree);
        this.staticText = "Cast any number of red instant and/or sorcery cards from your graveyard without paying their mana costs";
    }

    public ChandraAblazeEffect5(final ChandraAblazeEffect5 effect) {
        super(effect);
    }

    @Override
    public ChandraAblazeEffect5 copy() {
        return new ChandraAblazeEffect5(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            FilterCard filter = new FilterCard("red instant or sorcery card from your graveyard to play");
            filter.add(new ColorPredicate(ObjectColor.RED));
            filter.add(Predicates.or(
                    new CardTypePredicate(CardType.INSTANT),
                    new CardTypePredicate(CardType.SORCERY)));

            String message = "Play red instant or sorcery card from your graveyard without paying its mana cost?";
            Set<Card> cards = player.getGraveyard().getCards(filter, game);
            TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
            while (!cards.isEmpty() && player.chooseUse(outcome, message, source, game)) {
                target.clearChosen();
                if (player.choose(outcome, target, source.getSourceId(), game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        player.cast(card.getSpellAbility(), game, true);
                        player.getGraveyard().remove(card);
                        cards.remove(card);
                    }
                }
            }

            return true;
        }
        return false;
    }
}

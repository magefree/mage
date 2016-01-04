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
package mage.sets.oathofthegatewatch;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class OathOfNissa extends CardImpl {

    public OathOfNissa(UUID ownerId) {
        super(ownerId, 140, "Oath of Nissa", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "OGW";
        this.supertype.add("Legendary");

        // When Oath of Nissa enters the battlefield, look at the top three cards of your library. You may reveal a creature, land, or planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OathOfNissaEffect()));

        // You may spend mana as though it were mana of any color to cast planeswalker spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OathOfNissaSpendAnyManaEffect()));
    }

    public OathOfNissa(final OathOfNissa card) {
        super(card);
    }

    @Override
    public OathOfNissa copy() {
        return new OathOfNissa(this);
    }
}

class OathOfNissaEffect extends OneShotEffect {

    private final static FilterCard filter = new FilterCard("a creature, land, or planeswalker card");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER),
                new CardTypePredicate(CardType.LAND)));
    }

    public OathOfNissaEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top three cards of your library. You may reveal a creature, land, or planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in any order";
    }

    public OathOfNissaEffect(final OathOfNissaEffect effect) {
        super(effect);
    }

    @Override
    public OathOfNissaEffect copy() {
        return new OathOfNissaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Cards topCards = new CardsImpl();
            topCards.addAll(controller.getLibrary().getTopCards(game, 3));
            if (!topCards.isEmpty()) {
                controller.lookAtCards(sourceObject.getIdName(), topCards, game);
                int number = topCards.count(filter, source.getSourceId(), source.getControllerId(), game);
                if (number > 0) {
                    if (controller.chooseUse(outcome, "Reveal a creature, land, or planeswalker card from the looked at cards and put it into your hand?", source, game)) {
                        Card card;
                        if (number == 1) {
                            card = topCards.getCards(filter, source.getSourceId(), source.getControllerId(), game).iterator().next();
                        } else {
                            Target target = new TargetCard(Zone.LIBRARY, filter);
                            controller.chooseTarget(outcome, target, source, game);
                            card = topCards.get(target.getFirstTarget(), game);
                        }
                        if (card != null) {
                            controller.moveCards(card, Zone.HAND, source, game);
                            controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
                            topCards.remove(card);
                        }
                    }
                    controller.putCardsOnBottomOfLibrary(topCards, game, source, true);
                }
            }
            return true;
        }
        return false;
    }
}

class OathOfNissaSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public OathOfNissaSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.Custom, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast planeswalker spells";
    }

    public OathOfNissaSpendAnyManaEffect(final OathOfNissaSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OathOfNissaSpendAnyManaEffect copy() {
        return new OathOfNissaSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.getControllerId().equals(affectedControllerId)) {
            MageObject mageObject = game.getObject(objectId);
            if (mageObject != null) {
                if (mageObject.getCardType().contains(CardType.PLANESWALKER)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}

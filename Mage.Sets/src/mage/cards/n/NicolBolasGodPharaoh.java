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
package mage.cards.n;

import java.util.HashMap;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Will
 */
public class NicolBolasGodPharaoh extends CardImpl {

    private UUID exileId = UUID.randomUUID();
    private static final FilterPermanent opponentsNonlandPermanentsFilter = new FilterNonlandPermanent("non-land permanents your opponents control");
    static {
        opponentsNonlandPermanentsFilter.add(new ControllerPredicate(TargetController.OPPONENT));
    }
    public NicolBolasGodPharaoh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.PLANESWALKER},"{4}{U}{B}{R}");
        this.subtype.add("Bolas");

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(7));

        // +2: Target opponent exiles cards from the top of his or her library until he or she exiles a nonland card. Until end of turn, you may cast that card without paying its mana cost.
        LoyaltyAbility ability = new LoyaltyAbility(new NicolBolasGodPharaohPlusTwoEffect(exileId), 2);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // +1: Each opponent exiles two cards from his or her hand.
        this.addAbility(new LoyaltyAbility(new NicolBolasGodPharaohPlusOneEffect(exileId), 1));

        // -4: Nicol Bolas, God-Pharaoh deals 7 damage to target creature or player.
        ability = new LoyaltyAbility(new DamageTargetEffect(7), -2);
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);

        // -12: Exile each nonland permanent your opponents control.
        this.addAbility(new LoyaltyAbility(new ExileAllEffect(opponentsNonlandPermanentsFilter, exileId, this.getIdName()), -12));
    }

    public NicolBolasGodPharaoh(final NicolBolasGodPharaoh card) {
        super(card);
    }

    @Override
    public NicolBolasGodPharaoh copy() {
        return new NicolBolasGodPharaoh(this);
    }
}

class NicolBolasGodPharaohPlusOneEffect extends OneShotEffect {
    
    private UUID exileId;
    
    NicolBolasGodPharaohPlusOneEffect(UUID exileId) {
        super(Outcome.Exile);
        this.exileId = exileId;
        this.staticText = "Each opponent exiles two cards from his or her hand.";
    }
    
    NicolBolasGodPharaohPlusOneEffect(final NicolBolasGodPharaohPlusOneEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public NicolBolasGodPharaohPlusOneEffect copy() {
        return new NicolBolasGodPharaohPlusOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Store for each player the cards to exile, that's important because all exile shall happen at the same time
        HashMap<UUID, Cards> cardsToExile = new HashMap<>();
        // Each player chooses 2 cards to discard
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
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
            player.moveCardsToExile(cardsPlayerChoseToExile.getCards(game), source, game, true, exileId, source.getSourceObject(game).getIdName());
        }
        return true;
    }
}

class NicolBolasGodPharaohPlusTwoEffect extends OneShotEffect {

    private UUID exileId;
    
    public NicolBolasGodPharaohPlusTwoEffect(UUID exileId) {
        super(Outcome.Detriment);
        this.exileId = exileId;
        this.staticText = "Target opponent exiles cards from the top of his or her library until he or she exiles a nonland card. Until end of turn, you may cast that card without paying its mana cost";
    }

    public NicolBolasGodPharaohPlusTwoEffect(final NicolBolasGodPharaohPlusTwoEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public NicolBolasGodPharaohPlusTwoEffect copy() {
        return new NicolBolasGodPharaohPlusTwoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (opponent != null && opponent.getLibrary().hasCards() && sourceObject != null) {
            Library library = opponent.getLibrary();
            Card card;
            do {
                card = library.removeFromTop(game);
                if (card != null) {
                    opponent.moveCardsToExile(card, source, game, true, exileId, sourceObject.getIdName());
                }
            } while (library.hasCards() && card != null && card.isLand());

            if (card != null) {
                ContinuousEffect effect = new NicolBolasGodPharaohFromExileEffect();
                effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class NicolBolasGodPharaohFromExileEffect extends AsThoughEffectImpl {

    public NicolBolasGodPharaohFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast card from exile";
    }

    public NicolBolasGodPharaohFromExileEffect(final NicolBolasGodPharaohFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public NicolBolasGodPharaohFromExileEffect copy() {
        return new NicolBolasGodPharaohFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId != null && sourceId.equals(getTargetPointer().getFirst(game, source))
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(sourceId);
            if (card != null && game.getState().getZone(sourceId) == Zone.EXILED) {
                Player player = game.getPlayer(affectedControllerId);
                player.setCastSourceIdWithAlternateMana(sourceId, null, card.getSpellAbility().getCosts());
                return true;
            }
        }
        return false;
    }
}

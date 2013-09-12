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
package mage.sets.theros;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class AshiokNightmareWeaver extends CardImpl<AshiokNightmareWeaver> {

    public AshiokNightmareWeaver(UUID ownerId) {
        super(ownerId, 188, "Ashiok, Nightmare Weaver", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{B}");
        this.expansionSetCode = "THS";
        this.subtype.add("Ashiok");

        this.color.setBlue(true);
        this.color.setBlack(true);

        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(3)), false));

        // +2: Exile the top three cards of target opponent's library.
        LoyaltyAbility ability = new LoyaltyAbility(new AshiokNightmareWeaverExileEffect(), 2);
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);

        // -X: Put a creature card with converted mana cost X exiled with Ashiok, Nightmare Weaver onto the battlefield under your control. That creature is a Nightmare in addition to its other types.
        this.addAbility(new LoyaltyAbility(new AshiokNightmareWeaverPutIntoPlayEffect()));

        // -10: Exile all cards from all opponents' hands and graveyards.);
        this.addAbility(new LoyaltyAbility(new AshiokNightmareWeaverExileAllEffect(), -10));

    }

    public AshiokNightmareWeaver(final AshiokNightmareWeaver card) {
        super(card);
    }

    @Override
    public AshiokNightmareWeaver copy() {
        return new AshiokNightmareWeaver(this);
    }
}

class AshiokNightmareWeaverExileEffect extends OneShotEffect<AshiokNightmareWeaverExileEffect> {

    public AshiokNightmareWeaverExileEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile the top three cards of target opponent's library";
    }

    public AshiokNightmareWeaverExileEffect(final AshiokNightmareWeaverExileEffect effect) {
        super(effect);
    }

    @Override
    public AshiokNightmareWeaverExileEffect copy() {
        return new AshiokNightmareWeaverExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            for (int i = 0; i < 3; i++) {
                Card card = opponent.getLibrary().getFromTop(game);
                if (card != null) {
                    card.moveToExile(exileId, "Ashiok, Nightmare Weaver", source.getSourceId(), game);
                }
            }
        }
        return false;
    }
}

class AshiokNightmareWeaverPutIntoPlayEffect extends OneShotEffect<AshiokNightmareWeaverPutIntoPlayEffect> {

    public AshiokNightmareWeaverPutIntoPlayEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a creature card with converted mana cost X exiled with {this} onto the battlefield under your control. That creature is a Nightmare in addition to its other types";
    }

    public AshiokNightmareWeaverPutIntoPlayEffect(final AshiokNightmareWeaverPutIntoPlayEffect effect) {
        super(effect);
    }

    @Override
    public AshiokNightmareWeaverPutIntoPlayEffect copy() {
        return new AshiokNightmareWeaverPutIntoPlayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        int cmc = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                cmc = ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }

        FilterCard filter = new FilterCreatureCard(new StringBuilder("creature card with converted mana cost {").append(cmc).append("} exiled with Ashiok, Nightmare Weaver").toString());
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.Equal, cmc));
        Target target = new TargetCardInExile(filter, CardUtil.getCardExileZoneId(game, source));

        if (target.canChoose(source.getSourceId(), player.getId(), game)) {
            if (player.chooseTarget(Outcome.PutCreatureInPlay, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null && card.putOntoBattlefield(game, Zone.EXILED, source.getSourceId(), player.getId())) {
                    ContinuousEffectImpl effect = new AshiokNightmareWeaverAddTypeEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    game.addEffect(effect, source);
                    return true;
                }
            }
        }
        return false;
    }
}

class AshiokNightmareWeaverAddTypeEffect extends ContinuousEffectImpl<AshiokNightmareWeaverAddTypeEffect> {

    public AshiokNightmareWeaverAddTypeEffect() {
        super(Duration.Custom, Outcome.Neutral);
        staticText = "That creature is a Nightmare in addition to its other types";
    }

    public AshiokNightmareWeaverAddTypeEffect(final AshiokNightmareWeaverAddTypeEffect effect) {
        super(effect);
    }

    @Override
    public AshiokNightmareWeaverAddTypeEffect copy() {
        return new AshiokNightmareWeaverAddTypeEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (creature != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        creature.getSubtype().add("Nightmare");
                    }
                    break;
            }
            return true;
        } else {
            this.used = true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }

}

class AshiokNightmareWeaverExileAllEffect extends OneShotEffect<AshiokNightmareWeaverExileAllEffect> {

    public AshiokNightmareWeaverExileAllEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile all cards from all opponents' hands and graveyards";
    }

    public AshiokNightmareWeaverExileAllEffect(final AshiokNightmareWeaverExileAllEffect effect) {
        super(effect);
    }

    @Override
    public AshiokNightmareWeaverExileAllEffect copy() {
        return new AshiokNightmareWeaverExileAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID exileId = CardUtil.getCardExileZoneId(game, source);
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                for (UUID cardId :opponent.getHand()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        card.moveToExile(exileId, "Ashiok, Nightmare Weaver", source.getSourceId(), game);
                    }
                }
                for (UUID cardId :opponent.getGraveyard()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        card.moveToExile(exileId, "Ashiok, Nightmare Weaver", source.getSourceId(), game);
                    }
                }
            }
        }
        return true;
    }
}

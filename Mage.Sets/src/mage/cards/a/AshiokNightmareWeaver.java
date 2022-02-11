
package mage.cards.a;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AshiokNightmareWeaver extends CardImpl {

    public AshiokNightmareWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ASHIOK);

        this.setStartingLoyalty(3);

        // +2: Exile the top three cards of target opponent's library.
        LoyaltyAbility ability = new LoyaltyAbility(new AshiokNightmareWeaverExileEffect(), 2);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // -X: Put a creature card with converted mana cost X exiled with Ashiok, Nightmare Weaver onto the battlefield under your control. That creature is a Nightmare in addition to its other types.
        this.addAbility(new LoyaltyAbility(new AshiokNightmareWeaverPutIntoPlayEffect()));

        // -10: Exile all cards from all opponents' hands and graveyards.);
        this.addAbility(new LoyaltyAbility(new AshiokNightmareWeaverExileAllEffect(), -10));

    }

    private AshiokNightmareWeaver(final AshiokNightmareWeaver card) {
        super(card);
    }

    @Override
    public AshiokNightmareWeaver copy() {
        return new AshiokNightmareWeaver(this);
    }
}

class AshiokNightmareWeaverExileEffect extends OneShotEffect {

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
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && opponent != null && controller != null) {
            UUID exileZone = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
            if (exileZone != null) {
                controller.moveCardsToExile(opponent.getLibrary().getTopCards(game, 3), source, game, true, exileZone, sourceObject.getIdName());
                return true;
            }
        }
        return false;
    }
}

class AshiokNightmareWeaverPutIntoPlayEffect extends OneShotEffect {

    public AshiokNightmareWeaverPutIntoPlayEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a creature card with mana value X exiled with {this} onto the battlefield under your control. That creature is a Nightmare in addition to its other types";
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
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null || controller == null) {
            return false;
        }

        int cmc = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                cmc = ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }

        FilterCard filter = new FilterCreatureCard("creature card with mana value {" + cmc + "} exiled with " + sourceObject.getIdName());
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, cmc));

        Target target = new TargetCardInExile(filter, CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()));

        if (target.canChoose(source.getSourceId(), controller.getId(), game)) {
            if (controller.chooseTarget(Outcome.PutCreatureInPlay, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null
                        && controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        ContinuousEffectImpl effect = new AshiokNightmareWeaverAddTypeEffect();
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        game.addEffect(effect, source);
                    }
                }
            }
        }
        return true;
    }
}

class AshiokNightmareWeaverAddTypeEffect extends ContinuousEffectImpl {

    public AshiokNightmareWeaverAddTypeEffect() {
        super(Duration.Custom, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
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
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (creature == null) {
            this.used = true;
            return false;
        }
        creature.addSubType(game, SubType.NIGHTMARE);
        return true;
    }
}

class AshiokNightmareWeaverExileAllEffect extends OneShotEffect {

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
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null || controller == null) {
            return false;
        }
        UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        if (exileId == null) {
            return false;
        }
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                Cards cards = new CardsImpl(opponent.getHand());
                for (UUID cardId : cards) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, exileId, sourceObject.getIdName(), source, game, Zone.HAND, true);
                    }
                }
                cards.clear();
                cards.addAll(opponent.getGraveyard());
                for (UUID cardId : cards) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, exileId, sourceObject.getIdName(), source, game, Zone.GRAVEYARD, true);
                    }
                }
            }
        }
        return true;
    }
}

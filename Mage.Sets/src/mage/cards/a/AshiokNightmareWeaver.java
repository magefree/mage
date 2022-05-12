package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
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
        if (opponent == null || controller == null) {
            return false;
        }
        controller.moveCardsToExile(
                opponent.getLibrary().getTopCards(game, 3), source, game, true,
                CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source)
        );
        return true;
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
        if (controller == null) {
            return false;
        }

        int cmc = CardUtil.castStream(
                source.getCosts().stream(), PayVariableLoyaltyCost.class
        ).mapToInt(VariableCostImpl::getAmount).sum();

        FilterCard filter = new FilterCreatureCard("creature card with mana value " + cmc);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, cmc));

        Target target = new TargetCardInExile(filter, CardUtil.getExileZoneId(game, source));

        if (!target.canChoose(controller.getId(), source, game)) {
            return false;
        }
        controller.chooseTarget(Outcome.PutCreatureInPlay, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return true;
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null) {
            game.addEffect(new AddCardSubTypeTargetEffect(
                    SubType.NIGHTMARE, Duration.EndOfTurn
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
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
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            cards.addAll(opponent.getHand());
            cards.addAll(opponent.getGraveyard());
        }
        controller.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        return true;
    }
}

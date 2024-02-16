package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.LilianaWakerOfTheDeadEmblem;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class LilianaWakerOfTheDead extends CardImpl {

    private static final DynamicValue cardsInGraveyard = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CARDS, null);
    private static final DynamicValue xValue = new SignInversionDynamicValue(cardsInGraveyard);
    private static final Hint hint = new ValueHint("Cards in your graveyard", cardsInGraveyard);

    public LilianaWakerOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{B}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);
        this.setStartingLoyalty(4);

        // +1: Each player discards a card. Each opponent who can't loses 3 life.
        this.addAbility(new LoyaltyAbility(new LilianaWakerOfTheDeadDiscardEffect(), 1));

        // −3: Target creature gets -X/-X until end of turn, where X is the number of cards in your graveyard.
        Ability ability = new LoyaltyAbility(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), -3);
        ability.addHint(hint);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // −7: You get an emblem with "At the beginning of combat on your turn, put target creature card from a graveyard onto the battlefield under your control. It gains haste."
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new LilianaWakerOfTheDeadEmblem()), -7));
    }

    private LilianaWakerOfTheDead(final LilianaWakerOfTheDead card) {
        super(card);
    }

    @Override
    public LilianaWakerOfTheDead copy() {
        return new LilianaWakerOfTheDead(this);
    }
}

class LilianaWakerOfTheDeadDiscardEffect extends OneShotEffect {

    LilianaWakerOfTheDeadDiscardEffect() {
        super(Outcome.Discard);
        staticText = "Each player discards a card. Each opponent who can't loses 3 life";
    }

    private LilianaWakerOfTheDeadDiscardEffect(LilianaWakerOfTheDeadDiscardEffect effect) {
        super(effect);
    }

    @Override
    public LilianaWakerOfTheDeadDiscardEffect copy() {
        return new LilianaWakerOfTheDeadDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Map<UUID, Cards> cardsToDiscard = new HashMap<>();
        if (controller == null) {
            return true;
        }
        // choose cards to discard
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int numberOfCardsToDiscard = Math.min(1, player.getHand().size());
            Cards cards = new CardsImpl();
            Target target = new TargetDiscard(numberOfCardsToDiscard, numberOfCardsToDiscard, StaticFilters.FILTER_CARD, playerId);
            player.chooseTarget(outcome, target, source, game);
            cards.addAll(target.getTargets());
            cardsToDiscard.put(playerId, cards);
        }
        // discard all chosen cards
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int amountDiscarded = player.discard(cardsToDiscard.get(playerId), false, source, game).size();
            if (controller.hasOpponent(playerId, game) && amountDiscarded == 0) {
                player.loseLife(3, game, source, false);
            }
        }
        return true;
    }
}

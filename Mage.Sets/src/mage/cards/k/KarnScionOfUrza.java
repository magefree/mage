package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.KarnConstructToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetOpponent;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author spjspj
 */
public final class KarnScionOfUrza extends CardImpl {

    public KarnScionOfUrza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KARN);
        this.setStartingLoyalty(5);

        // +1: Reveal the top two cards of your library. An opponent chooses one of them. Put that card into your hand and exile the other with a silver counter on it.
        this.addAbility(new LoyaltyAbility(new KarnPlus1Effect(), 1));

        // -1: Put a card you own with a silver counter on it from exile into your hand.
        this.addAbility(new LoyaltyAbility(new KarnMinus1Effect(), -1));

        // -2: Create a 0/0 colorless Construct artifact creature token with "This creature gets +1/+1 for each artifact you control."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(
                new KarnConstructToken(), 1
        ), -2).addHint(ArtifactYouControlHint.instance));
    }

    private KarnScionOfUrza(final KarnScionOfUrza card) {
        super(card);
    }

    @Override
    public KarnScionOfUrza copy() {
        return new KarnScionOfUrza(this);
    }
}

class KarnPlus1Effect extends OneShotEffect {

    KarnPlus1Effect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top two cards of your library. An opponent chooses one of them. " +
                "Put that card into your hand and exile the other with a silver counter on it.";
    }

    private KarnPlus1Effect(final KarnPlus1Effect effect) {
        super(effect);
    }

    @Override
    public KarnPlus1Effect copy() {
        return new KarnPlus1Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 2));
        if (cards.isEmpty()) {
            return true;
        }
        controller.revealCards(source, cards, game);
        Card cardToHand;
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                cardToHand = cards.getRandom(game);
                break;
            default:
                Player opponent;
                Set<UUID> opponents = game.getOpponents(controller.getId());
                if (opponents.size() == 1) {
                    opponent = game.getPlayer(opponents.iterator().next());
                } else {
                    Target target = new TargetOpponent(true);
                    controller.chooseTarget(Outcome.Detriment, target, source, game);
                    opponent = game.getPlayer(target.getFirstTarget());
                }
                TargetCard target = new TargetCard(1, Zone.LIBRARY, StaticFilters.FILTER_CARD);
                opponent.chooseTarget(outcome, cards, target, source, game);
                cardToHand = game.getCard(target.getFirstTarget());
        }
        if (cardToHand != null) {
            controller.moveCards(cardToHand, Zone.HAND, source, game);
        }

        cards.retainZone(Zone.LIBRARY, game);
        if (cards.isEmpty()) {
            return true;
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        cards.getCards(game)
                .stream()
                .forEach(card -> card.addCounters(CounterType.SILVER.createInstance(1), source, game));
        return true;
    }
}

class KarnMinus1Effect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("card you own with a silver counter on it in exile");

    static {
        filter.add(CounterType.SILVER.getPredicate());
    }

    KarnMinus1Effect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Put a card you own with a silver counter on it from exile into your hand";
    }

    private KarnMinus1Effect(final KarnMinus1Effect effect) {
        super(effect);
    }

    @Override
    public KarnMinus1Effect copy() {
        return new KarnMinus1Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(game
                .getExile()
                .getCards(filter, game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> card.isOwnedBy(source.getControllerId()))
                .collect(Collectors.toList()));
        Card card;
        switch (cards.size()) {
            case 0:
                return false;
            case 1:
                card = cards.getRandom(game);
                break;
            default:
                TargetCard target = new TargetCardInExile(filter);
                target.setNotTarget(true);
                controller.choose(outcome, target, source, game);
                card = cards.get(target.getFirstTarget(), game);
        }
        if (card == null) {
            return false;
        }
        return card != null && controller.moveCards(card, Zone.HAND, source, game);
    }
}

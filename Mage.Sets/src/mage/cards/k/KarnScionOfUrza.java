package mage.cards.k;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.token.KarnConstructToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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
        LoyaltyAbility ability1 = new LoyaltyAbility(new KarnPlus1Effect(), 1);
        this.addAbility(ability1);

        // -1: Put a card you own with a silver counter on it from exile into your hand.
        LoyaltyAbility ability2 = new LoyaltyAbility(new KarnMinus1Effect(), -1);
        this.addAbility(ability2);

        // -2: Create a 0/0 colorless Construct artifact creature token with "This creature gets +1/+1 for each artifact you control."
        LoyaltyAbility ability3 = new LoyaltyAbility(new CreateTokenEffect(new KarnConstructToken(), 1), -2);
        ability3.addHint(ArtifactYouControlHint.instance);
        this.addAbility(ability3);
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
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject == null || controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 2));

        if (cards.isEmpty()) {
            return true;
        }
        controller.revealCards(staticText, cards, game);
        Card cardToHand;
        if (cards.size() == 1) {
            cardToHand = cards.getRandom(game);
        } else {
            Player opponent;
            Set<UUID> opponents = game.getOpponents(controller.getId());
            if (opponents.size() == 1) {
                opponent = game.getPlayer(opponents.iterator().next());
            } else {
                Target target = new TargetOpponent(true);
                controller.chooseTarget(Outcome.Detriment, target, source, game);
                opponent = game.getPlayer(target.getFirstTarget());
            }
            TargetCard target = new TargetCard(1, Zone.LIBRARY, new FilterCard());
            opponent.chooseTarget(outcome, cards, target, source, game);
            cardToHand = game.getCard(target.getFirstTarget());
        }
        if (cardToHand != null) {
            controller.moveCards(cardToHand, Zone.HAND, source, game);
            cards.remove(cardToHand);
        }

        if (!cards.isEmpty()) {
            controller.moveCards(cards, Zone.EXILED, source, game);
            for (Card c : cards.getCards(game)) {
                c.addCounters(CounterType.SILVER.createInstance(1), source.getControllerId(), source, game);
            }
        }
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
        ExileZone exZone = game.getExile().getPermanentExile(); // getExileZone(Zone.EXILED);
        if (exZone == null) {
            return true;
        }
        Card card = null;

        List<Card> exile = game.getExile().getAllCards(game);
        boolean noTargets = exile.isEmpty();
        if (noTargets) {
            game.informPlayer(controller, "You have no exiled cards.");
            return true;
        }

        Cards filteredCards = new CardsImpl();

        for (Card exileCard : exile) {
            if (exileCard.isOwnedBy(source.getControllerId()) && filter.match(exileCard, game)) {
                filteredCards.add(exileCard);
            }
        }

        TargetCard target = new TargetCard(Zone.EXILED, filter);
        target.setNotTarget(true);
        if (!controller.choose(Outcome.Benefit, filteredCards, target, game)) {
            return true;
        }
        if (card == null) {
            card = game.getCard(target.getFirstTarget());
        }
        if (card != null) {
            controller.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }
}

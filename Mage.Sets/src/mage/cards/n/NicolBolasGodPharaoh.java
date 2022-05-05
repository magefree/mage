package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.common.FilterNonlandPermanent;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Will
 */
public final class NicolBolasGodPharaoh extends CardImpl {

    private static final FilterCreaturePlayerOrPlaneswalker damageFilter
            = new FilterCreaturePlayerOrPlaneswalker("opponent, creature an opponent controls, or planeswalker an opponent controls.");
    private static final FilterPermanent exileFilter = new FilterNonlandPermanent();

    static {
        damageFilter.getPlayerFilter().add(TargetController.OPPONENT.getPlayerPredicate());
        damageFilter.getPermanentFilter().add(TargetController.OPPONENT.getControllerPredicate());
        exileFilter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public NicolBolasGodPharaoh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{B}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BOLAS);

        this.setStartingLoyalty(7);

        // +2: Target opponent exiles cards from the top of their library until they exile a nonland card. Until end of turn, you may cast that card without paying its mana cost.
        LoyaltyAbility ability = new LoyaltyAbility(new NicolBolasGodPharaohPlusTwoEffect(), 2);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // +1: Each opponent exiles two cards from their hand.
        this.addAbility(new LoyaltyAbility(new NicolBolasGodPharaohPlusOneEffect(), 1));

        // -4: Nicol Bolas, God-Pharaoh deals 7 damage to target opponent, creature an opponent controls, or planeswalker an opponent controls.
        ability = new LoyaltyAbility(new DamageTargetEffect(7), -4);
        ability.addTarget(new TargetAnyTarget(damageFilter));
        this.addAbility(ability);

        // -12: Exile each nonland permanent your opponents control.
        this.addAbility(new LoyaltyAbility(new ExileAllEffect(exileFilter)
                .setText("exile each nonland permanent your opponents control"), -12));
    }

    private NicolBolasGodPharaoh(final NicolBolasGodPharaoh card) {
        super(card);
    }

    @Override
    public NicolBolasGodPharaoh copy() {
        return new NicolBolasGodPharaoh(this);
    }
}

class NicolBolasGodPharaohPlusOneEffect extends OneShotEffect {

    NicolBolasGodPharaohPlusOneEffect() {
        super(Outcome.Exile);
        this.staticText = "Each opponent exiles two cards from their hand.";
    }

    private NicolBolasGodPharaohPlusOneEffect(final NicolBolasGodPharaohPlusOneEffect effect) {
        super(effect);
    }

    @Override
    public NicolBolasGodPharaohPlusOneEffect copy() {
        return new NicolBolasGodPharaohPlusOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Boolean applied = false;
        // Store for each player the cards to exile, that's important because all exile shall happen at the same time
        Map<UUID, Cards> cardsToExile = new HashMap<>();

        // Each player chooses 2 cards to discard
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            int numberOfCardsToExile = Math.min(2, opponent.getHand().size());
            if (numberOfCardsToExile > 0) {
                Target target = new TargetCardInHand(numberOfCardsToExile, new FilterCard());
                target.setRequired(true);
                if (opponent.chooseTarget(Outcome.Exile, target, source, game)) {
                    Cards cards = new CardsImpl(target.getTargets());
                    cardsToExile.put(opponentId, cards);
                }
            } else {
                cardsToExile.put(opponentId, new CardsImpl());
            }
        }

        // Exile all chosen cards at the same time
        Cards cardsOpponentsChoseToExile = new CardsImpl();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null || !cardsToExile.containsKey(opponentId)) {
            }
            cardsOpponentsChoseToExile.addAll(cardsToExile.get(opponentId));
            opponent.moveCards(cardsOpponentsChoseToExile, Zone.EXILED, source, game);
            applied = true;
        }
        return applied;
    }
}

class NicolBolasGodPharaohPlusTwoEffect extends OneShotEffect {

    NicolBolasGodPharaohPlusTwoEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target opponent exiles cards from the top of their "
                + "library until they exile a nonland card. Until end of turn, "
                + "you may cast that card without paying its mana cost";
    }

    private NicolBolasGodPharaohPlusTwoEffect(final NicolBolasGodPharaohPlusTwoEffect effect) {
        super(effect);
    }

    @Override
    public NicolBolasGodPharaohPlusTwoEffect copy() {
        return new NicolBolasGodPharaohPlusTwoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (opponent == null) {
            return false;
        }
        Library library = opponent.getLibrary();
        Card card;
        do {
            card = library.getFromTop(game);
            if (card == null) {
                continue;
            }
            opponent.moveCards(card, Zone.EXILED, source, game);
            if (card.isLand(game)) {
                continue;
            }
            ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, TargetController.YOU, Duration.EndOfTurn, true);
            effect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect, source);
            break;
        } while (library.hasCards()
                && card != null);
        return true;
    }
}

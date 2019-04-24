package mage.cards.n;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileAllEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Will
 */
public final class NicolBolasGodPharaoh extends CardImpl {

    private static final FilterPermanent opponentsNonlandPermanentsFilter = new FilterNonlandPermanent("non-land permanents your opponents control");

    static {
        opponentsNonlandPermanentsFilter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public NicolBolasGodPharaoh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{4}{U}{B}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BOLAS);

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(7));

        // +2: Target opponent exiles cards from the top of their library until he or she exiles a nonland card. Until end of turn, you may cast that card without paying its mana cost.
        LoyaltyAbility ability = new LoyaltyAbility(new NicolBolasGodPharaohPlusTwoEffect(), 2);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // +1: Each opponent exiles two cards from their hand.
        this.addAbility(new LoyaltyAbility(new NicolBolasGodPharaohPlusOneEffect(), 1));

        // -4: Nicol Bolas, God-Pharaoh deals 7 damage to any target.
        ability = new LoyaltyAbility(new DamageTargetEffect(7), -4);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // -12: Exile each nonland permanent your opponents control.
        this.addAbility(new LoyaltyAbility(new ExileAllEffect(opponentsNonlandPermanentsFilter), -12));
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

    NicolBolasGodPharaohPlusOneEffect() {
        super(Outcome.Exile);
        this.staticText = "Each opponent exiles two cards from their hand.";
    }

    NicolBolasGodPharaohPlusOneEffect(final NicolBolasGodPharaohPlusOneEffect effect) {
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
            if (opponent != null) {
                int numberOfCardsToExile = Math.min(2, opponent.getHand().size());
                Target target = new TargetCardInHand(numberOfCardsToExile, new FilterCard());
                target.setRequired(true);
                if (opponent.chooseTarget(Outcome.Exile, target, source, game)) {
                    Cards cards = new CardsImpl(target.getTargets());
                    cardsToExile.put(opponentId, cards);
                }
            }
        }
        // Exile all chosen cards at the same time
        Cards cardsOpponentsChoseToExile = new CardsImpl();
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                cardsOpponentsChoseToExile.addAll(cardsToExile.get(opponentId));
                opponent.moveCards(cardsOpponentsChoseToExile, Zone.EXILED, source, game);
                applied = true;
            }
        }
        return applied;
    }
}

class NicolBolasGodPharaohPlusTwoEffect extends OneShotEffect {

    public NicolBolasGodPharaohPlusTwoEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target opponent exiles cards from the top of their "
                + "library until he or she exiles a nonland card. Until end of turn, "
                + "you may cast that card without paying its mana cost";
    }

    public NicolBolasGodPharaohPlusTwoEffect(final NicolBolasGodPharaohPlusTwoEffect effect) {
        super(effect);
    }

    @Override
    public NicolBolasGodPharaohPlusTwoEffect copy() {
        return new NicolBolasGodPharaohPlusTwoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (opponent != null) {
            Library library = opponent.getLibrary();
            Card card;
            do {
                card = library.getFromTop(game);
                if (card != null) {
                    opponent.moveCards(card, Zone.EXILED, source, game);
                    if (!card.isLand()) {
                        ContinuousEffect effect = new NicolBolasGodPharaohFromExileEffect();
                        effect.setTargetPointer(new FixedTarget(card.getId(), 
                                game.getState().getZoneChangeCounter(card.getId())));
                        game.addEffect(effect, source);
                        break;
                    }
                }
            } while (library.hasCards() 
                    && card != null);
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
        if (sourceId != null
                && sourceId.equals(getTargetPointer().getFirst(game, source))
                && affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(sourceId);
            if (card != null
                    && game.getState().getZone(sourceId) == Zone.EXILED) {
                Player controller = game.getPlayer(affectedControllerId);
                if (controller != null) {
                    controller.setCastSourceIdWithAlternateMana(
                            sourceId,
                            null,
                            card.getSpellAbility().getCosts());
                    return true;
                }
            }
        }
        return false;
    }
}

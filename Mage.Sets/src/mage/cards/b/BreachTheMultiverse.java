package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreachTheMultiverse extends CardImpl {

    public BreachTheMultiverse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}{B}");

        // Each player mills ten cards. For each player, choose a creature or planeswalker card in that player's graveyard. Put those cards onto the battlefield under your control. Then each creature you control becomes a Phyrexian in addition to its other types.
        this.getSpellAbility().addEffect(new MillCardsEachPlayerEffect(10, TargetController.EACH_PLAYER));
        this.getSpellAbility().addEffect(new BreachTheMultiverseEffect());
        this.getSpellAbility().addEffect(new AddCardSubtypeAllEffect(
                StaticFilters.FILTER_CONTROLLED_CREATURE, SubType.PHYREXIAN, DependencyType.AddingCreatureType
        ).setText("Then each creature you control becomes a Phyrexian in addition to its other types"));
    }

    private BreachTheMultiverse(final BreachTheMultiverse card) {
        super(card);
    }

    @Override
    public BreachTheMultiverse copy() {
        return new BreachTheMultiverse(this);
    }
}

class BreachTheMultiverseEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("creature or planeswalker card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    BreachTheMultiverseEffect() {
        super(Outcome.Benefit);
        staticText = "For each player, choose a creature or planeswalker card in that player's graveyard. " +
                "Put those cards onto the battlefield under your control";
    }

    private BreachTheMultiverseEffect(final BreachTheMultiverseEffect effect) {
        super(effect);
    }

    @Override
    public BreachTheMultiverseEffect copy() {
        return new BreachTheMultiverseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        TargetCard target = new TargetCardInGraveyard(filter);
        target.setNotTarget(true);
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || player.getGraveyard().count(filter, game) < 1) {
                continue;
            }
            target.clearChosen();
            target.withChooseHint("from " + player.getName() + "'s graveyard");
            controller.choose(Outcome.PutCreatureInPlay, player.getGraveyard(), target, source, game);
            cards.add(target.getFirstTarget());
        }
        return controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}

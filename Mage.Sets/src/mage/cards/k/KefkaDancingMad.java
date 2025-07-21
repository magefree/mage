package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KefkaDancingMad extends CardImpl {

    public KefkaDancingMad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // During your turn, Kefka has indestructible.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "during your turn, {this} has indestructible"
        )));

        // At the beginning of your end step, exile a card at random from each opponent's graveyard. You may cast any number of spells from among cards exiled this way without paying their mana costs. Then each player who owns a spell you cast this way loses life equal to its mana value.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new KefkaDancingMadEffect()));
    }

    private KefkaDancingMad(final KefkaDancingMad card) {
        super(card);
    }

    @Override
    public KefkaDancingMad copy() {
        return new KefkaDancingMad(this);
    }
}

class KefkaDancingMadEffect extends OneShotEffect {

    private static final class KefkaDancingMadTracker implements CardUtil.SpellCastTracker {

        private final Map<UUID, Integer> map = new HashMap<>();

        @Override
        public boolean checkCard(Card card, Game game) {
            return true;
        }

        @Override
        public void addCard(Card card, Ability source, Game game) {
            map.compute(card.getOwnerId(), (u, i) -> i == null ? card.getManaValue() : Integer.sum(i, card.getManaValue()));
        }

        private void loseLife(UUID playerId, Game game, Ability source) {
            int totalCost = map.getOrDefault(playerId, 0);
            if (totalCost > 0) {
                Optional.ofNullable(playerId)
                        .map(game::getPlayer)
                        .ifPresent(player -> player.loseLife(totalCost, game, source, false));
            }
        }
    }

    KefkaDancingMadEffect() {
        super(Outcome.Benefit);
        staticText = "exile a card at random from each opponent's graveyard. " +
                "You may cast any number of spells from among cards exiled this way without paying their mana costs. " +
                "Then each player who owns a spell you cast this way loses life equal to its mana value";
    }

    private KefkaDancingMadEffect(final KefkaDancingMadEffect effect) {
        super(effect);
    }

    @Override
    public KefkaDancingMadEffect copy() {
        return new KefkaDancingMadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                cards.add(player.getGraveyard().getRandom(game));
            }
        }
        if (cards.isEmpty()) {
            return false;
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        game.processAction();
        cards.retainZone(Zone.EXILED, game);
        KefkaDancingMadTracker tracker = new KefkaDancingMadTracker();
        CardUtil.castMultipleWithAttributeForFree(
                controller, source, game, cards, StaticFilters.FILTER_CARD,
                Integer.MAX_VALUE, tracker, false
        );
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            tracker.loseLife(playerId, game, source);
        }
        return true;
    }
}

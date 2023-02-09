package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetDiscard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class SkullslitherWorm extends CardImpl {

    public SkullslitherWorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.WORM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Skullslither Worm enters the battlefield, each opponent discards a card.
        // For each opponent who can't, put two +1/+1 counters on Skullslither Worm.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SkullslitherWormEffect()));
    }

    private SkullslitherWorm(final SkullslitherWorm card) {
        super(card);
    }

    @Override
    public SkullslitherWorm copy() {
        return new SkullslitherWorm(this);
    }
}

class SkullslitherWormEffect extends OneShotEffect {

    SkullslitherWormEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent discards a card. For each opponent who can't, put two +1/+1 counters on {this}";
    }

    private SkullslitherWormEffect(final SkullslitherWormEffect effect) {
        super(effect);
    }

    @Override
    public SkullslitherWormEffect copy() {
        return new SkullslitherWormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Map<UUID, Cards> cardsToDiscard = new HashMap<>();

        // choose cards to discard
        for (UUID playerId : game.getOpponents(source.getControllerId(), true)) {
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
        for (UUID playerId : game.getOpponents(source.getControllerId(), true)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int amountDiscarded = player.discard(cardsToDiscard.get(playerId), false, source, game).size();
            if (amountDiscarded == 0 && permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(2), source, game);
            }
        }
        return true;
    }
}

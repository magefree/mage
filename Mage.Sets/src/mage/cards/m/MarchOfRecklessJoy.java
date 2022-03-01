package mage.cards.m;

import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.costadjusters.ExileCardsFromHandAdjuster;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarchOfRecklessJoy extends CardImpl {

    private static final FilterCard filter = new FilterCard("red cards from your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public MarchOfRecklessJoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // As an additional cost to cast this spell, you may exile any number of red cards from your hand. This spell costs {2} less to cast for each card exiled this way.
        ExileCardsFromHandAdjuster.addAdjusterAndMessage(this, filter);

        // Exile the top X cards of your library. You may play up to two of those cards until the end of your next turn.
        this.getSpellAbility().addEffect(new MarchOfRecklessJoyEffect());
        this.getSpellAbility().addWatcher(new MarchOfRecklessJoyWatcher());
    }

    private MarchOfRecklessJoy(final MarchOfRecklessJoy card) {
        super(card);
    }

    @Override
    public MarchOfRecklessJoy copy() {
        return new MarchOfRecklessJoy(this);
    }
}

class MarchOfRecklessJoyEffect extends OneShotEffect {

    public MarchOfRecklessJoyEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile the top X cards of your library. " +
                "You may play up to two of those cards until the end of your next turn.";
    }

    public MarchOfRecklessJoyEffect(final MarchOfRecklessJoyEffect effect) {
        super(effect);
    }

    @Override
    public MarchOfRecklessJoyEffect copy() {
        return new MarchOfRecklessJoyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int xValue = source.getManaCostsToPay().getX();
        if (player == null || xValue < 1 || player.getLibrary().size() < 1) {
            return false;
        }
        Set<Card> cards = player.getLibrary().getTopCards(game, xValue);
        player.moveCardsToExile(
                cards, source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        Condition condition = new MarchOfRecklessJoyCondition(source);
        for (Card card : cards) {
            CardUtil.makeCardPlayable(
                    game, source, card, Duration.UntilEndOfYourNextTurn,
                    false, null, condition
            );
        }
        return true;
    }
}

class MarchOfRecklessJoyCondition implements Condition {

    private final MageObjectReference mor;

    MarchOfRecklessJoyCondition(Ability source) {
        this.mor = new MageObjectReference(source);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return MarchOfRecklessJoyWatcher.check(mor, game);
    }
}

class MarchOfRecklessJoyWatcher extends Watcher {

    private final Map<MageObjectReference, Integer> morMap = new HashMap<>();

    public MarchOfRecklessJoyWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST || event.getAdditionalReference() == null) {
            return;
        }
        morMap.compute(event
                        .getAdditionalReference()
                        .getApprovingMageObjectReference(),
                CardUtil::setOrIncrementValue
        );
    }

    static boolean check(MageObjectReference mor, Game game) {
        return game.getState().getWatcher(MarchOfRecklessJoyWatcher.class).morMap.getOrDefault(mor, 0) < 2;
    }
}

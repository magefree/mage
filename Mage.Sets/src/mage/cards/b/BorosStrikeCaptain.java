package mage.cards.b;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.BattalionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class BorosStrikeCaptain extends CardImpl {

    public BorosStrikeCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/W}{R/W}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Battalion -- Whenever Boros Strike-Captain and at least two other creatures attack, exile the top card of your library. During any turn you attacked with three or more creatures, you may play that card.
        this.addAbility(new BattalionAbility(new BorosStrikeCaptainEffect())
                .addHint(BorosStrikeCaptainCondition.getHint()), new BorosStrikeCaptainWatcher());
    }

    private BorosStrikeCaptain(final BorosStrikeCaptain card) {
        super(card);
    }

    @Override
    public BorosStrikeCaptain copy() {
        return new BorosStrikeCaptain(this);
    }
}

class BorosStrikeCaptainEffect extends OneShotEffect {

    BorosStrikeCaptainEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. During any turn you attacked " +
                "with three or more creatures, you may play that card";
    }

    private BorosStrikeCaptainEffect(final BorosStrikeCaptainEffect effect) {
        super(effect);
    }

    @Override
    public BorosStrikeCaptainEffect copy() {
        return new BorosStrikeCaptainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CardUtil.makeCardPlayable(
                game, source, card, false, Duration.Custom, false,
                player.getId(), BorosStrikeCaptainCondition.instance
        );
        return true;
    }
}

enum BorosStrikeCaptainCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return BorosStrikeCaptainWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "you attacked with three or more creatures this turn";
    }
}

class BorosStrikeCaptainWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> map = new HashMap<>();

    BorosStrikeCaptainWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ATTACKER_DECLARED) {
            return;
        }
        Optional.ofNullable(event)
                .map(GameEvent::getTargetId)
                .map(game::getPermanent)
                .ifPresent(permanent -> map
                        .computeIfAbsent(permanent.getControllerId(), x -> new HashSet<>())
                        .add(new MageObjectReference(permanent, game)));

    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(BorosStrikeCaptainWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), Collections.emptySet())
                .size() >= 3;
    }
}

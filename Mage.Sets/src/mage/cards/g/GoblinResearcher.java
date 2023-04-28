package mage.cards.g;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
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
public final class GoblinResearcher extends CardImpl {

    public GoblinResearcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Goblin Researcher enters the battlefield, exile the top card of your library. During any turn you attacked with Goblin Researcher, you may play that card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GoblinResearcherEffect()), new GoblinResearcherWatcher());
    }

    private GoblinResearcher(final GoblinResearcher card) {
        super(card);
    }

    @Override
    public GoblinResearcher copy() {
        return new GoblinResearcher(this);
    }
}

class GoblinResearcherEffect extends OneShotEffect {

    GoblinResearcherEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. " +
                "During any turn you attacked with {this}, you may play that card";
    }

    private GoblinResearcherEffect(final GoblinResearcherEffect effect) {
        super(effect);
    }

    @Override
    public GoblinResearcherEffect copy() {
        return new GoblinResearcherEffect(this);
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
                game, source, card, Duration.Custom, false,
                source.getControllerId(), GoblinResearcherCondition.instance
        );
        return true;
    }
}

enum GoblinResearcherCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}

class GoblinResearcherWatcher extends Watcher {

    private final Map<MageObjectReference, Set<UUID>> map = new HashMap<>();

    GoblinResearcherWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ATTACKER_DECLARED) {
            return;
        }
        map.computeIfAbsent(new MageObjectReference(event.getSourceId(), game), x -> new HashSet<>()).add(event.getPlayerId());
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean checkPlayer(Ability source, Game game) {
        return game.getState()
                .getWatcher(GoblinResearcherWatcher.class)
                .map
                .getOrDefault(new MageObjectReference(source, 0), Collections.emptySet())
                .contains(source.getControllerId());
    }
}

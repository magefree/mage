package mage.cards.y;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.common.CyclingDiscardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YidaroWanderingMonster extends CardImpl {

    public YidaroWanderingMonster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Cycling {1}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{R}")));

        // When you cycle Yidaro, Wandering Monster, shuffle it into your library from your graveyard. If you've cycled a card named Yidaro, Wandering Monster four or more times this game, put it onto the battlefield from your graveyard instead.
        this.addAbility(new CycleTriggeredAbility(new YidaroWanderingMonsterEffect())
                .addHint(YidaroWanderingMonsterHint.instance), new YidaroWanderingMonsterWatcher());
    }

    private YidaroWanderingMonster(final YidaroWanderingMonster card) {
        super(card);
    }

    @Override
    public YidaroWanderingMonster copy() {
        return new YidaroWanderingMonster(this);
    }
}

class YidaroWanderingMonsterEffect extends OneShotEffect {

    YidaroWanderingMonsterEffect() {
        super(Outcome.Benefit);
        staticText = "shuffle it into your library from your graveyard. " +
                "If you've cycled a card named Yidaro, Wandering Monster four or more times this game, " +
                "put it onto the battlefield from your graveyard instead.";
    }

    private YidaroWanderingMonsterEffect(final YidaroWanderingMonsterEffect effect) {
        super(effect);
    }

    @Override
    public YidaroWanderingMonsterEffect copy() {
        return new YidaroWanderingMonsterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Costs<Cost> costs = (Costs) this.getValue("cycleCosts");
        if (costs == null) {
            return false;
        }
        MageObjectReference cycledCard = costs
                .stream()
                .filter(CyclingDiscardCost.class::isInstance)
                .map(CyclingDiscardCost.class::cast)
                .map(CyclingDiscardCost::getCycledCard)
                .findFirst()
                .orElse(null);
        if (cycledCard == null || game.getState().getZone(cycledCard.getSourceId()) != Zone.GRAVEYARD) {
            return false;
        }
        Card card = cycledCard.getCard(game);
        if (card == null) {
            return false;
        }
        YidaroWanderingMonsterWatcher watcher = game.getState().getWatcher(YidaroWanderingMonsterWatcher.class);
        if (watcher == null || watcher.getYidaroCount(player.getId()) < 4) {
            player.putCardsOnBottomOfLibrary(card, game, source, true);
            player.shuffleLibrary(source, game);
        } else {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}

enum YidaroWanderingMonsterHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Player player = game.getPlayer(ability.getControllerId());
        YidaroWanderingMonsterWatcher watcher = game.getState().getWatcher(YidaroWanderingMonsterWatcher.class);
        if (player == null || watcher == null) {
            return "";
        }
        return player.getName() + " has cycled a card named Yidaro, Wandering Monster " + watcher.getYidaroCount(player.getId()) + " times this game";
    }

    @Override
    public Hint copy() {
        return instance;
    }
}

class YidaroWanderingMonsterWatcher extends Watcher {

    private final Map<UUID, Integer> countMap = new HashMap();

    YidaroWanderingMonsterWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATED_ABILITY) {
            return;
        }
        StackObject object = game.getStack().getStackObject(event.getSourceId());
        if (object == null || !(object.getStackAbility() instanceof CyclingAbility)) {
            return;
        }
        Card card = game.getCard(object.getSourceId());
        if (card != null && "Yidaro, Wandering Monster".equals(card.getName())) {
            countMap.merge(object.getControllerId(), 1, Integer::sum);
        }
    }

    int getYidaroCount(UUID playerId) {
        return countMap.getOrDefault(playerId, 0);
    }
}

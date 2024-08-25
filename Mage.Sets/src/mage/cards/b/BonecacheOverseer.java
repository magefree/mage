package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class BonecacheOverseer extends CardImpl {

    public BonecacheOverseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}, Pay 1 life: Draw a card. Activate only if three or more cards left your graveyard this turn or if you've sacrificed a Food this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new TapSourceCost(), BonecacheOverseerCondition.instance
        );
        ability.addCost(new PayLifeCost(1));
        this.addAbility(ability.addHint(BonecacheOverseerCondition.getHint()), new BonecacheOverseerWatcher());
    }

    private BonecacheOverseer(final BonecacheOverseer card) {
        super(card);
    }

    @Override
    public BonecacheOverseer copy() {
        return new BonecacheOverseer(this);
    }
}

enum BonecacheOverseerCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            instance, "Three or more cards left your graveyard " +
            "this turn or you've sacrificed a Food this turn"
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return BonecacheOverseerWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "three or more cards left your graveyard this turn or if you've sacrificed a Food this turn";
    }
}

class BonecacheOverseerWatcher extends Watcher {

    private final Map<UUID, Integer> graveyardMap = new HashMap<>();
    private final Set<UUID> foodSet = new HashSet<>();

    BonecacheOverseerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case ZONE_CHANGE:
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if (!Zone.GRAVEYARD.match(zEvent.getFromZone())) {
                    return;
                }
                Card card = game.getCard(zEvent.getTargetId());
                if (card != null) {
                    graveyardMap.compute(card.getOwnerId(), CardUtil::setOrIncrementValue);
                }
                return;
            case SACRIFICED_PERMANENT:
                Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
                if (permanent != null && permanent.hasSubtype(SubType.FOOD, game)) {
                    foodSet.add(permanent.getControllerId());
                }
        }
    }

    @Override
    public void reset() {
        super.reset();
        graveyardMap.clear();
        foodSet.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(BonecacheOverseerWatcher.class)
                .check(source.getControllerId());
    }

    private boolean check(UUID playerId) {
        return foodSet.contains(playerId) || graveyardMap.getOrDefault(playerId, 0) >= 3;
    }
}

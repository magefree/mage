package mage.cards.w;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarHistorian extends CardImpl {

    public WarHistorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // War Historian has indestructible as long as it attacked a battle this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance()), WarHistorianCondition.instance,
                "{this} has indestructible as long as it attacked a battle this turn"
        )).addHint(WarHistorianCondition.getHint()), new WarHistorianWatcher());
    }

    private WarHistorian(final WarHistorian card) {
        super(card);
    }

    @Override
    public WarHistorian copy() {
        return new WarHistorian(this);
    }
}

enum WarHistorianCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance, "Attacked a battle this turn");

    @Override
    public boolean apply(Game game, Ability source) {
        return WarHistorianWatcher.checkPermanent(source, game);
    }

    public static Hint getHint() {
        return hint;
    }
}

class WarHistorianWatcher extends Watcher {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    WarHistorianWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ATTACKER_DECLARED) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isBattle(game)) {
            morSet.add(new MageObjectReference(event.getSourceId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        morSet.clear();
    }

    static boolean checkPermanent(Ability source, Game game) {
        return game
                .getState()
                .getWatcher(WarHistorianWatcher.class)
                .morSet
                .stream()
                .anyMatch(mor -> mor.refersTo(source.getSourceId(), game));
    }
}

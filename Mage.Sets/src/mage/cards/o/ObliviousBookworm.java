package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObliviousBookworm extends CardImpl {

    public ObliviousBookworm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, you may draw a card. If you do, discard a card unless a permanent entered the battlefield face down under your control this turn or you turned a permanent face up this turn.
        Ability ability = new BeginningOfEndStepTriggeredAbility(TargetController.YOU,
                new DrawCardSourceControllerEffect(1), true
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DiscardControllerEffect(1), ObliviousBookwormCondition.instance, "If you do, discard " +
                "a card unless a permanent entered the battlefield face down under your control " +
                "this turn or you turned a permanent face up this turn"
        ));
        this.addAbility(ability.addHint(ObliviousBookwormCondition.getHint()), new ObliviousBookwormWatcher());
    }

    private ObliviousBookworm(final ObliviousBookworm card) {
        super(card);
    }

    @Override
    public ObliviousBookworm copy() {
        return new ObliviousBookworm(this);
    }
}

enum ObliviousBookwormCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(
            new InvertCondition(instance), "You had a face-down " +
            "permanent enter or turned a permanent face-up this turn"
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return !ObliviousBookwormWatcher.checkPlayer(game, source);
    }
}

class ObliviousBookwormWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    ObliviousBookwormWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
                if (permanent != null && permanent.isFaceDown(game)) {
                    set.add(permanent.getControllerId());
                }
                return;
            case TURNED_FACE_UP:
                set.add(event.getPlayerId());
        }
    }

    @Override
    public void reset() {
        set.clear();
        super.reset();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(ObliviousBookwormWatcher.class)
                .set
                .contains(source.getControllerId());
    }
}

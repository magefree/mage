package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Blitzball extends CardImpl {

    public Blitzball(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // GOOOOAAAALLL! -- {T}, Sacrifice this artifact: Draw two cards. Activate only if an opponent was dealt combat damage by a legendary creature this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DrawCardSourceControllerEffect(2),
                new TapSourceCost(), BlitzballCondition.instance
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability.addHint(BlitzballCondition.getHint()).withFlavorWord("GOOOOAAAALLL!"), new BlitzballWatcher());
    }

    private Blitzball(final Blitzball card) {
        super(card);
    }

    @Override
    public Blitzball copy() {
        return new Blitzball(this);
    }
}

enum BlitzballCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return BlitzballWatcher.checkPlayer(game, source);
    }

    @Override
    public String toString() {
        return "an opponent was dealt combat damage by a legendary creature this turn";
    }
}

class BlitzballWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    BlitzballWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                && ((DamagedPlayerEvent) event).isCombatDamage()
                && Optional
                .ofNullable(event)
                .map(GameEvent::getSourceId)
                .map(game::getPermanent)
                .filter(permanent -> permanent.isLegendary(game))
                .filter(permanent -> permanent.isCreature(game))
                .isPresent()) {
            set.addAll(game.getOpponents(event.getTargetId()));
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(BlitzballWatcher.class)
                .set
                .contains(source.getControllerId());
    }
}

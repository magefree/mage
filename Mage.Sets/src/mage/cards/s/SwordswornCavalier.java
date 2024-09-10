package mage.cards.s;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.Watcher;

import java.util.*;

/**
 * @author TheElk801
 */
public final class SwordswornCavalier extends CardImpl {

    public SwordswornCavalier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Swordsworn Cavalier has first strike as long as another Knight entered the battlefield under your control this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield),
                SwordswornCavalierWatcher::checkPermanent, "{this} has first strike as long as " +
                "another Knight entered the battlefield under your control this turn"
        )), new SwordswornCavalierWatcher());
    }

    private SwordswornCavalier(final SwordswornCavalier card) {
        super(card);
    }

    @Override
    public SwordswornCavalier copy() {
        return new SwordswornCavalier(this);
    }
}

class SwordswornCavalierWatcher extends Watcher {

    private final Map<UUID, Set<MageObjectReference>> map = new HashMap<>();

    SwordswornCavalierWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            return;
        }
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent.hasSubtype(SubType.KNIGHT, game)) {
            map.computeIfAbsent(permanent.getControllerId(), x -> new HashSet<>())
                    .add(new MageObjectReference(permanent, game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        map.clear();
    }

    static boolean checkPermanent(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(SwordswornCavalierWatcher.class)
                .map
                .getOrDefault(source.getControllerId(), Collections.emptySet())
                .stream()
                .anyMatch(mor -> !mor.refersTo(source, game));
    }
}

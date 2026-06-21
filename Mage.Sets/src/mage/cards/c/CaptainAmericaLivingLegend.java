package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author muz
 */
public final class CaptainAmericaLivingLegend extends CardImpl {

    public CaptainAmericaLivingLegend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever a creature you control becomes tapped during your turn, if it's the first time that creature has become tapped this turn, untap it.
        this.addAbility(new CaptainAmericaLivingLegendTriggeredAbility(), new CaptainAmericaLivingLegendWatcher());
    }

    private CaptainAmericaLivingLegend(final CaptainAmericaLivingLegend card) {
        super(card);
    }

    @Override
    public CaptainAmericaLivingLegend copy() {
        return new CaptainAmericaLivingLegend(this);
    }
}

class CaptainAmericaLivingLegendTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("creature you control");

    CaptainAmericaLivingLegendTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UntapTargetEffect("untap it"), false);
        setTriggerPhrase("Whenever a creature you control becomes tapped during your turn, ");
        withInterveningIf(CaptainAmericaLivingLegendCondition.instance);
    }

    private CaptainAmericaLivingLegendTriggeredAbility(final CaptainAmericaLivingLegendTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CaptainAmericaLivingLegendTriggeredAbility copy() {
        return new CaptainAmericaLivingLegendTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.isActivePlayer(getControllerId())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (!filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
        return true;
    }
}

enum CaptainAmericaLivingLegendCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return source
            .getEffects()
            .get(0)
            .getTargetPointer()
            .getTargets(game, source)
            .stream()
            .findFirst()
            .map(objectId -> game
                .getState()
                .getWatcher(CaptainAmericaLivingLegendWatcher.class)
                .getTappedCount(objectId) == 1)
            .orElse(false);
    }

    @Override
    public String toString() {
        return "it's the first time that creature has become tapped this turn";
    }
}

class CaptainAmericaLivingLegendWatcher extends Watcher {

    private final Map<UUID, Integer> tappedCounts = new HashMap<>();

    CaptainAmericaLivingLegendWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.TAPPED) {
            tappedCounts.compute(event.getTargetId(), (key, oldValue) -> oldValue == null ? 1 : oldValue + 1);
        }
    }

    @Override
    public void reset() {
        super.reset();
        tappedCounts.clear();
    }

    int getTappedCount(UUID objectId) {
        return tappedCounts.getOrDefault(objectId, 0);
    }
}

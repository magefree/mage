package mage.cards.u;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.abilities.keyword.StationAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.BoostCountersAddedFirstTimeWatcher;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class USSEnterpriseDGalaxyClass extends CardImpl {

    public USSEnterpriseDGalaxyClass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // Whenever one or more charge counters are put on U.S.S. Enterprise-D for the first time each turn, exile the top card of your library. You may play that card this turn.
        this.addAbility(new USSEnterpriseDGalaxyClassTriggeredAbility());

        // Station
        this.addAbility(new StationAbility());

        // STATION 7+
        // Flying
        // Vigilance
        // 4/5
        this.addAbility(new StationLevelAbility(7)
            .withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(VigilanceAbility.getInstance())
            .withPT(4, 5));
    }

    private USSEnterpriseDGalaxyClass(final USSEnterpriseDGalaxyClass card) {
        super(card);
    }

    @Override
    public USSEnterpriseDGalaxyClass copy() {
        return new USSEnterpriseDGalaxyClass(this);
    }
}

class USSEnterpriseDGalaxyClassTriggeredAbility extends TriggeredAbilityImpl {

    USSEnterpriseDGalaxyClassTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn));
        this.setTriggerPhrase("Whenever one or more charge counters are put on {this} for the first time each turn, ");
        this.addWatcher(new BoostCountersAddedFirstTimeWatcher());
    }

    private USSEnterpriseDGalaxyClassTriggeredAbility(final USSEnterpriseDGalaxyClassTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public USSEnterpriseDGalaxyClassTriggeredAbility copy() {
        return new USSEnterpriseDGalaxyClassTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null
                && this.getSourceId().equals(event.getTargetId())
                && event.getData().equals(CounterType.CHARGE.getName())
                && BoostCountersAddedFirstTimeWatcher.checkEvent(event, permanent, game, 0);
    }
}

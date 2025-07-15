package mage.cards.s;

import mage.MageInt;
import mage.MageItem;
import mage.MageObject;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetNonlandPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SyrVondamSunstarExemplar extends CardImpl {

    public SyrVondamSunstarExemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever another creature you control dies or is put into exile, put a +1/+1 counter on Syr Vondam and you gain 1 life.
        this.addAbility(new SyrVondamSunstarExemplarFirstTriggeredAbility());

        // When Syr Vondam dies or is put into exile while its power is 4 or greater, destroy up to one target nonland permanent.
        this.addAbility(new SyrVondamSunstarExemplarSecondTriggeredAbility());
    }

    private SyrVondamSunstarExemplar(final SyrVondamSunstarExemplar card) {
        super(card);
    }

    @Override
    public SyrVondamSunstarExemplar copy() {
        return new SyrVondamSunstarExemplar(this);
    }
}

class SyrVondamSunstarExemplarFirstTriggeredAbility extends TriggeredAbilityImpl {

    SyrVondamSunstarExemplarFirstTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        this.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.setTriggerPhrase("Whenever another creature you control dies or is put into exile, ");
        this.setLeavesTheBattlefieldTrigger(true);
    }

    private SyrVondamSunstarExemplarFirstTriggeredAbility(final SyrVondamSunstarExemplarFirstTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SyrVondamSunstarExemplarFirstTriggeredAbility copy() {
        return new SyrVondamSunstarExemplarFirstTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return Zone.BATTLEFIELD.match(zEvent.getFromZone())
                && (Zone.GRAVEYARD.match(zEvent.getToZone()) || Zone.EXILED.match(zEvent.getToZone()))
                && StaticFilters
                .FILTER_ANOTHER_CREATURE_YOU_CONTROL
                .match(zEvent.getTarget(), getControllerId(), this, game);
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }
}


class SyrVondamSunstarExemplarSecondTriggeredAbility extends TriggeredAbilityImpl {

    SyrVondamSunstarExemplarSecondTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
        this.addTarget(new TargetNonlandPermanent(0, 1));
        this.setTriggerPhrase("When {this} dies or is put into exile while its power is 4 or greater, ");
        this.setLeavesTheBattlefieldTrigger(true);
    }

    private SyrVondamSunstarExemplarSecondTriggeredAbility(final SyrVondamSunstarExemplarSecondTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SyrVondamSunstarExemplarSecondTriggeredAbility copy() {
        return new SyrVondamSunstarExemplarSecondTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return Zone.BATTLEFIELD.match(zEvent.getFromZone())
                && (Zone.GRAVEYARD.match(zEvent.getToZone()) || Zone.EXILED.match(zEvent.getToZone()))
                && Optional
                .ofNullable(zEvent)
                .map(ZoneChangeEvent::getTarget)
                .filter(permanent -> permanent.getPower().getValue() >= 4)
                .map(MageItem::getId)
                .filter(getSourceId()::equals)
                .isPresent();
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject sourceObject, GameEvent event) {
        return TriggeredAbilityImpl.isInUseableZoneDiesTrigger(this, sourceObject, event, game);
    }
}

package mage.cards.e;

import java.util.UUID;

import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class ElectricSeaweed extends CardImpl {

    public ElectricSeaweed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When this creature enters, until end of turn, whenever another creature dies, this creature deals 1 damage to each non-Wall creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
            new ElectricSeaweedDelayedTriggeredAbility(), false
        )));

        // {T}: This creature deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private ElectricSeaweed(final ElectricSeaweed card) {
        super(card);
    }

    @Override
    public ElectricSeaweed copy() {
        return new ElectricSeaweed(this);
    }
}

class ElectricSeaweedDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Wall creature");

    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
    }

    public ElectricSeaweedDelayedTriggeredAbility() {
        super(new DamageAllEffect(1, filter), Duration.EndOfTurn, false);
    }

    private ElectricSeaweedDelayedTriggeredAbility(final ElectricSeaweedDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ElectricSeaweedDelayedTriggeredAbility copy() {
        return new ElectricSeaweedDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zoneChangeEvent = (ZoneChangeEvent) event;
        if (zoneChangeEvent.isDiesEvent()) {
            Permanent permanent = zoneChangeEvent.getTarget();
            return permanent != null && StaticFilters.FILTER_ANOTHER_CREATURE.match(permanent, game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever another creature dies, this creature deals 1 damage to each non-Wall creature.";
    }
}


package mage.cards.f;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class FabricationModule extends CardImpl {

    public FabricationModule(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // Whenever you get one or more {E}, put a +1/+1 counter on target creature you control.
        Ability ability = new FabricationModuleTriggeredAbility();
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {4}, {T}: You get {E}.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GetEnergyCountersControllerEffect(1), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private FabricationModule(final FabricationModule card) {
        super(card);
    }

    @Override
    public FabricationModule copy() {
        return new FabricationModule(this);
    }
}

class FabricationModuleTriggeredAbility extends TriggeredAbilityImpl {

    FabricationModuleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
    }

    private FabricationModuleTriggeredAbility(final FabricationModuleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FabricationModuleTriggeredAbility copy() {
        return new FabricationModuleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.ENERGY.getName())) {
            return Objects.equals(event.getTargetId(), this.getControllerId());
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you get one or more {E}, put a +1/+1 counter on target creature you control.";
    }
}

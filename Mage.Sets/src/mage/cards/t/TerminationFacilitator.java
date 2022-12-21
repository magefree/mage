package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TerminationFacilitator extends CardImpl {

    public TerminationFacilitator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Put a bounty counter on target creature or planeswalker. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), new TapSourceCost()
        );
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);

        // Whenever a creature or planeswalker an opponent controls with a bounty counter on it is dealt damage, destroy it.
        this.addAbility(new TerminationFacilitatorTriggeredAbility());
    }

    private TerminationFacilitator(final TerminationFacilitator card) {
        super(card);
    }

    @Override
    public TerminationFacilitator copy() {
        return new TerminationFacilitator(this);
    }
}

class TerminationFacilitatorTriggeredAbility extends TriggeredAbilityImpl {

    TerminationFacilitatorTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DestroyTargetEffect());
    }

    private TerminationFacilitatorTriggeredAbility(final TerminationFacilitatorTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TerminationFacilitatorTriggeredAbility copy() {
        return new TerminationFacilitatorTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.getCounters(game).containsKey(CounterType.BOUNTY)
                && game.getOpponents(getControllerId()).contains(permanent.getControllerId())
                && (permanent.isCreature(game) || permanent.isPlaneswalker(game))) {
            this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature or planeswalker an opponent controls with a bounty counter on it is dealt damage, destroy it.";
    }
}

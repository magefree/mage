package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DelifsCube extends CardImpl {

    public DelifsCube(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {2}, {tap}: This turn, when target creature you control attacks and isn't blocked, it assigns no combat damage this turn and you put a cube counter on Delif's Cube.
        Ability ability = new SimpleActivatedAbility(
                new CreateDelayedTriggeredAbilityEffect(new DelifsCubeTriggeredAbility()), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {2}, Remove a cube counter from Delif's Cube: Regenerate target creature.
        ability = new SimpleActivatedAbility(new RegenerateTargetEffect(), new GenericManaCost(2));
        ability.addCost(new RemoveCountersSourceCost(CounterType.CUBE.createInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private DelifsCube(final DelifsCube card) {
        super(card);
    }

    @Override
    public DelifsCube copy() {
        return new DelifsCube(this);
    }
}

class DelifsCubeTriggeredAbility extends DelayedTriggeredAbility {

    DelifsCubeTriggeredAbility() {
        super(new AssignNoCombatDamageTargetEffect(Duration.EndOfTurn, "it assigns no combat damage this turn"), Duration.EndOfTurn, false, false);
        this.addEffect(new AddCountersSourceEffect(CounterType.CUBE.createInstance()));
    }

    private DelifsCubeTriggeredAbility(final DelifsCubeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNBLOCKED_ATTACKER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getFirstTarget());
    }

    @Override
    public DelifsCubeTriggeredAbility copy() {
        return new DelifsCubeTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "This turn, when target creature you control attacks and isn't blocked, " +
                "it assigns no combat damage this turn and you put a cube counter on {this}.";
    }
}

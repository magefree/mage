package mage.cards.b;

import java.util.Objects;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.hint.common.MetalcraftHint;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author Cguy7777
 */
public final class BrotherhoodScribe extends CardImpl {

    public BrotherhoodScribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Metalcraft -- {T}: You get {E}. Activate only if you control three or more artifacts.
        this.addAbility(new ConditionalActivatedAbility(
                new GetEnergyCountersControllerEffect(1),
                new TapSourceCost(),
                MetalcraftCondition.instance)
                .setAbilityWord(AbilityWord.METALCRAFT)
                .addHint(MetalcraftHint.instance));

        // Whenever you get one or more {E} during your turn, creatures you control get +1/+1 until end of turn.
        this.addAbility(new BrotherhoodScribeAbility());
    }

    private BrotherhoodScribe(final BrotherhoodScribe card) {
        super(card);
    }

    @Override
    public BrotherhoodScribe copy() {
        return new BrotherhoodScribe(this);
    }
}

class BrotherhoodScribeAbility extends TriggeredAbilityImpl {

    BrotherhoodScribeAbility() {
        super(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.EndOfTurn));
        setTriggerPhrase("Whenever you get one or more {E} during your turn, ");
    }

    private BrotherhoodScribeAbility(final BrotherhoodScribeAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getData().equals(CounterType.ENERGY.getName()) && game.isActivePlayer(this.getControllerId())) {
            return Objects.equals(event.getTargetId(), this.getControllerId());
        }
        return false;
    }

    @Override
    public BrotherhoodScribeAbility copy() {
        return new BrotherhoodScribeAbility(this);
    }
}

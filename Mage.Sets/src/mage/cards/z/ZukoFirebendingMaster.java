package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersControllerCount;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.constants.*;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author anonymous
 */
public final class ZukoFirebendingMaster extends CardImpl {

    private static final DynamicValue xValue = new CountersControllerCount(CounterType.EXPERIENCE);

    public ZukoFirebendingMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Firebending X where X is the number of experience counters you have.
        this.addAbility(new FirebendingAbility(xValue));

        // Whenever you cast a spell during combat, you get an experience counter.
        this.addAbility(new ZukoFirebendingMasterAbility());
    }

    private ZukoFirebendingMaster(final ZukoFirebendingMaster card) {
        super(card);
    }

    @Override
    public ZukoFirebendingMaster copy() {
        return new ZukoFirebendingMaster(this);
    }
}

class ZukoFirebendingMasterAbility extends SpellCastControllerTriggeredAbility {

    public ZukoFirebendingMasterAbility() {
        super(new AddCountersPlayersEffect(CounterType.EXPERIENCE.createInstance(), TargetController.YOU), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL);
        setTriggerPhrase("Whenever you cast a spell during combat, ");
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getTurnPhaseType() == TurnPhase.COMBAT && super.checkTrigger(event, game);
    }
}
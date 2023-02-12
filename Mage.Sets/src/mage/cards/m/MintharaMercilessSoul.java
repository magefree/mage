package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.RevoltWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MintharaMercilessSoul extends CardImpl {

    public MintharaMercilessSoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ward {X}, where X is the number of experience counters you have.
        DynamicValue value = new MintharaMercilessSoulCount();
        Ability ability = new WardAbility(value, "the number of experience counters you have");
        this.addAbility(ability);

        // At the beginning of your end step, if a permanent you controlled left the battlefield this turn, you get an experience counter.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new AddCountersPlayersEffect(CounterType.EXPERIENCE.createInstance(), TargetController.YOU),
                TargetController.YOU, RevoltCondition.instance, false
        ), new RevoltWatcher());

        // Creatures you control get +1/+0 for each experience counter you have.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(value, StaticValue.get(0),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_CREATURES, false)));
    }

    private MintharaMercilessSoul(final MintharaMercilessSoul card) {
        super(card);
    }

    @Override
    public MintharaMercilessSoul copy() {
        return new MintharaMercilessSoul(this);
    }
}

class MintharaMercilessSoulCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int amount = 0;
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player != null) {
            amount = player.getCounters().getCount(CounterType.EXPERIENCE);
        }
        return amount;
    }

    @Override
    public MintharaMercilessSoulCount copy() {
        return new MintharaMercilessSoulCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "experience counter you have";
    }
}

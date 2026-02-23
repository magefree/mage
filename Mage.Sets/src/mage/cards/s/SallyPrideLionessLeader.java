package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RedMutantToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SallyPrideLionessLeader extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURES_NON_TOKEN, null);
    private static final Hint hint = new ValueHint("Nontoken creatures you control", xValue);

    public SallyPrideLionessLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Sally Pride enters, create X 2/2 red Mutant creature tokens, where X is the number of nontoken creatures you control.
        this.addAbility(new EntersBattlefieldAbility(new CreateTokenEffect(new RedMutantToken(), xValue)).addHint(hint));

        // Whenever Sally Pride attacks, put a +1/+1 counter on each creature you control.
        this.addAbility(new AttacksTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE)));
    }

    private SallyPrideLionessLeader(final SallyPrideLionessLeader card) {
        super(card);
    }

    @Override
    public SallyPrideLionessLeader copy() {
        return new SallyPrideLionessLeader(this);
    }
}

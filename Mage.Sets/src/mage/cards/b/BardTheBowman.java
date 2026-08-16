package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class BardTheBowman extends CardImpl {

    public BardTheBowman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you draw your second card each turn, put a +1/+1 counter on target creature. It gains lifelink until end of turn.
        Ability ability = new DrawNthCardTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false, 2
        );
        ability.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance())
            .setText("It gains lifelink until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BardTheBowman(final BardTheBowman card) {
        super(card);
    }

    @Override
    public BardTheBowman copy() {
        return new BardTheBowman(this);
    }
}

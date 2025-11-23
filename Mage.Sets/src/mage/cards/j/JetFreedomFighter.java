package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JetFreedomFighter extends CardImpl {

    public JetFreedomFighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R/W}{R/W}{R/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Jet enters, he deals damage equal to the number of creatures you control to target creature an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new DamageTargetEffect(CreaturesYouControlCount.PLURAL)
                        .setText("he deals damage equal to the number of creatures " +
                                "you control to target creature an opponent controls")
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // When Jet dies, put a +1/+1 counter on each of up to two target creatures.
        ability = new DiesSourceTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);
    }

    private JetFreedomFighter(final JetFreedomFighter card) {
        super(card);
    }

    @Override
    public JetFreedomFighter copy() {
        return new JetFreedomFighter(this);
    }
}

package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetControlledLandPermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DaiLiAgents extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("creatures you control with +1/+1 counters on them");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Creatures you control with +1/+1 counters on them", xValue);

    public DaiLiAgents(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When this creature enters, earthbend 1, then earthbend 1.
        Ability ability = new EntersBattlefieldTriggeredAbility(new EarthbendTargetEffect(1).setText("earthbend 1"));
        ability.addEffect(new EarthbendTargetEffect(1)
                .setTargetPointer(new SecondTargetPointer())
                .concatBy(", then"));
        ability.addTarget(new TargetControlledLandPermanent().withChooseHint("first target"));
        ability.addTarget(new TargetControlledLandPermanent().withChooseHint("second target"));
        this.addAbility(ability.addHint(hint));

        // Whenever this creature attacks, each opponent loses X life and you gain X life, where X is the number of creatures you control with +1/+1 counters on them.
        ability = new AttacksTriggeredAbility(new LoseLifeOpponentsEffect(xValue).setText("each opponent loses X life"));
        ability.addEffect(new GainLifeEffect(xValue).setText("and you gain X life, where X is the number of creatures you control with +1/+1 counters on them"));
        this.addAbility(ability);
    }

    private DaiLiAgents(final DaiLiAgents card) {
        super(card);
    }

    @Override
    public DaiLiAgents copy() {
        return new DaiLiAgents(this);
    }
}

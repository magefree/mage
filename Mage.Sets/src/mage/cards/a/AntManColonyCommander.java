package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.PutCounterOnPermanentTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.InsectToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class AntManColonyCommander extends CardImpl {

    public AntManColonyCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Ant-Man attacks, you may pay {1}. When you do, put a +1/+1 counter on target creature.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
        );
        reflexive.addTarget(new TargetCreaturePermanent());
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
            reflexive,
            new ManaCostsImpl<>("{1}"),
            "Pay {1}?"
        )));

        // Whenever you put a +1/+1 counter on a creature, create a 1/1 green Insect creature token. This ability triggers only once each turn.
        Ability ability2 = new PutCounterOnPermanentTriggeredAbility(
            new CreateTokenEffect(new InsectToken()), CounterType.P1P1, StaticFilters.FILTER_PERMANENT_A_CREATURE
        ).setTriggersLimitEachTurn(1);
        this.addAbility(ability2);
    }

    private AntManColonyCommander(final AntManColonyCommander card) {
        super(card);
    }

    @Override
    public AntManColonyCommander copy() {
        return new AntManColonyCommander(this);
    }
}

package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.ConstructToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MyriadConstruct extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final DynamicValue xValue2 = new SourcePermanentPowerCount(false);

    public MyriadConstruct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        // If Myriad Construct was kicked, it enters the battlefield with a +1/+1 counter on it for each nonbasic land your opponents control.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(), xValue, false),
                KickedCondition.ONCE, "If {this} was kicked, it enters the battlefield " +
                "with a +1/+1 counter on it for each nonbasic land your opponents control.", ""
        ));

        // When Myriad Construct becomes the target of a spell, sacrifice it and create a number of 1/1 colourless Construct artifact creature tokens equal to its power.
        Ability ability = new SourceBecomesTargetTriggeredAbility(
                new SacrificeSourceEffect().setText("sacrifice it"), StaticFilters.FILTER_SPELL_A
        );
        ability.addEffect(new CreateTokenEffect(new ConstructToken(), xValue2)
                .setText("and create a number of 1/1 colorless Construct artifact creature tokens equal to its power"));
        this.addAbility(ability);
    }

    private MyriadConstruct(final MyriadConstruct card) {
        super(card);
    }

    @Override
    public MyriadConstruct copy() {
        return new MyriadConstruct(this);
    }
}

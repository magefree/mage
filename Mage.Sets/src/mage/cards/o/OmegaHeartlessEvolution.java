package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OmegaHeartlessEvolution extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledLandPermanent("nonbasic lands you control");

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);

    public OmegaHeartlessEvolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Wave Cannon -- When Omega enters, for each opponent, tap up to one target nonland permanent that opponent controls. Put X stun counters on each of those permanents and you gain X life, where X is the number of nonbasic lands you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect()
                .setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, tap up to one target nonland permanent that opponent controls"));
        ability.addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance(), xValue)
                .setTargetPointer(new EachTargetPointer())
                .setText("Put X stun counters on each of those permanents"));
        ability.addEffect(new GainLifeEffect(xValue).setText("and you gain X life, where X is the number of nonbasic lands you control"));
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_NON_LAND));
        this.addAbility(ability.withFlavorWord("Wave Cannon").setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true)));
    }

    private OmegaHeartlessEvolution(final OmegaHeartlessEvolution card) {
        super(card);
    }

    @Override
    public OmegaHeartlessEvolution copy() {
        return new OmegaHeartlessEvolution(this);
    }
}

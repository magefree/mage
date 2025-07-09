package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SzarelGenesisShepherd extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another nontoken permanent");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public SzarelGenesisShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may play lands from your graveyard.
        this.addAbility(new SimpleStaticAbility(PlayFromGraveyardControllerEffect.playLands()));

        // Whenever you sacrifice another nontoken permanent during your turn, put a number of +1/+1 counters equal to Szarel's power on up to one other target creature.
        Ability ability = new SacrificePermanentTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), SourcePermanentPowerValue.NOT_NEGATIVE)
                        .setText("put a number of +1/+1 counters equal to {this}'s power on up to one other target creature"), filter
        ).withTriggerCondition(MyTurnCondition.instance);
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private SzarelGenesisShepherd(final SzarelGenesisShepherd card) {
        super(card);
    }

    @Override
    public SzarelGenesisShepherd copy() {
        return new SzarelGenesisShepherd(this);
    }
}

package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LuluLoyalHollyphant extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("tapped creature you control");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public LuluLoyalHollyphant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, if a permanent you controlled left the battlefield this turn, put a +1/+1 counter on each tapped creature you control, then untap them.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter),
                TargetController.YOU, RevoltCondition.instance, false
        );
        ability.addEffect(new UntapAllEffect(filter).setText(", then untap them"));
        this.addAbility(ability);

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private LuluLoyalHollyphant(final LuluLoyalHollyphant card) {
        super(card);
    }

    @Override
    public LuluLoyalHollyphant copy() {
        return new LuluLoyalHollyphant(this);
    }
}

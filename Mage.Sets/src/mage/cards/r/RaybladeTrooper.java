package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.HumanSoldierToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaybladeTrooper extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("nontoken creature you control with a +1/+1 counter on it");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(CounterType.P1P1.getPredicate());
    }

    public RaybladeTrooper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, put a +1/+1 counter on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever a nontoken creature you control with a +1/+1 counter on it dies, create a 1/1 white Human Soldier creature token.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new HumanSoldierToken()), false, filter
        ));

        // Warp {1}{W}
        this.addAbility(new WarpAbility(this, "{1}{W}"));
    }

    private RaybladeTrooper(final RaybladeTrooper card) {
        super(card);
    }

    @Override
    public RaybladeTrooper copy() {
        return new RaybladeTrooper(this);
    }
}

package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TributaryVaulter extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.MERFOLK, "another target Merfolk you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public TributaryVaulter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature becomes tapped, another target Merfolk you control gets +2/+0 until end of turn.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new BoostTargetEffect(2, 0));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TributaryVaulter(final TributaryVaulter card) {
        super(card);
    }

    @Override
    public TributaryVaulter copy() {
        return new TributaryVaulter(this);
    }
}

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class TorchCourier extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public TorchCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Sacrifice Torch Courier: Another target creature gains haste until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(
                        HasteAbility.getInstance(),
                        Duration.EndOfTurn
                ), new SacrificeSourceCost()
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TorchCourier(final TorchCourier card) {
        super(card);
    }

    @Override
    public TorchCourier copy() {
        return new TorchCourier(this);
    }
}

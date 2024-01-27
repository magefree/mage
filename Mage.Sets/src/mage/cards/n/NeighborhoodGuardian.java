package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeighborhoodGuardian extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public NeighborhoodGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another creature with power 2 or less enters the battlefield under your control, target creature you control gets +1/+1 until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new BoostTargetEffect(1, 1), filter);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private NeighborhoodGuardian(final NeighborhoodGuardian card) {
        super(card);
    }

    @Override
    public NeighborhoodGuardian copy() {
        return new NeighborhoodGuardian(this);
    }
}

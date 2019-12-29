package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.UnblockedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Throatseeker extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.NINJA, "unblocked attacking Ninjas");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(UnblockedPredicate.instance);
    }

    public Throatseeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Unblocked attacking Ninjas you control have lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private Throatseeker(final Throatseeker card) {
        super(card);
    }

    @Override
    public Throatseeker copy() {
        return new Throatseeker(this);
    }
}

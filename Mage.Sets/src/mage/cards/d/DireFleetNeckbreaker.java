package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

/**
 *
 * @author JayDi85
 */
public final class DireFleetNeckbreaker extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.PIRATE, "attacking Pirates");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public DireFleetNeckbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Attacking Pirates you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield, filter)));
    }

    private DireFleetNeckbreaker(final DireFleetNeckbreaker card) {
        super(card);
    }

    @Override
    public DireFleetNeckbreaker copy() {
        return new DireFleetNeckbreaker(this);
    }
}

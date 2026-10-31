package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author muz
 */
public final class KirkManOfAction extends CardImpl {

    private static final FilterCreaturePermanent filter =
        new FilterCreaturePermanent("Officers, Artificers, Doctors, Pilots, Scientists, and Spacecraft creatures");

    static {
        filter.add(Predicates.or(
            SubType.OFFICER.getPredicate(),
            SubType.ARTIFICER.getPredicate(),
            SubType.DOCTOR.getPredicate(),
            SubType.PILOT.getPredicate(),
            SubType.SCIENTIST.getPredicate(), SubType.SPACECRAFT.getPredicate()
        ));
    }

    public KirkManOfAction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Officers, Artificers, Doctors, Pilots, Scientists, and Spacecraft creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
            2, 2, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private KirkManOfAction(final KirkManOfAction card) {
        super(card);
    }

    @Override
    public KirkManOfAction copy() {
        return new KirkManOfAction(this);
    }
}

package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class UltronMachineOverlord extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Robots and Constructs");

    static {
        filter.add(Predicates.or(
            SubType.ROBOT.getPredicate(),
            SubType.CONSTRUCT.getPredicate()
        ));
    }

    public UltronMachineOverlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other Robots and Constructs you control get +2/+2
        this.addAbility(new SimpleStaticAbility(
            new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)
        ));
    }

    private UltronMachineOverlord(final UltronMachineOverlord card) {
        super(card);
    }

    @Override
    public UltronMachineOverlord copy() {
        return new UltronMachineOverlord(this);
    }
}

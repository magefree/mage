package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.CrewSaddleIncreasedPowerAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CloudspireCaptain extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Mounts and Vehicles");

    static {
        filter.add(Predicates.or(
                SubType.MOUNT.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public CloudspireCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Mounts and Vehicles you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter)));

        // This creature saddles Mounts and crews Vehicles as though its power were 2 greater.
        this.addAbility(new CrewSaddleIncreasedPowerAbility());
    }

    private CloudspireCaptain(final CloudspireCaptain card) {
        super(card);
    }

    @Override
    public CloudspireCaptain copy() {
        return new CloudspireCaptain(this);
    }
}

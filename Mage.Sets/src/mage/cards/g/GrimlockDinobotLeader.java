package mage.cards.g;

import mage.MageObject;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TransformablePredicate;

import java.util.UUID;

/**
 * @author Saga
 */
public final class GrimlockDinobotLeader extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dinosaurs and Vehicles you control");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Transformers creatures");
    static {
        filter.add(Predicates.<MageObject>or(
                SubType.DINOSAUR.getPredicate(),
                SubType.VEHICLE.getPredicate())
        );
        filter2.add(TransformablePredicate.instance);
    }

    public GrimlockDinobotLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.AUTOBOT}, "{1}{R}{G}{W}",
                "Grimlock, Ferocious King",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.DINOSAUR}, "RGW"
        );

        // Grimlock, Dinobot Leader
        this.getLeftHalfCard().setPT(4, 4);

        // Dinosaurs, Vehicles and other Transformers creatures you control get +2/+0.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield, filter, false)));
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield, filter2, true)));

        // {2}: Grimlock, Dinobot Leader becomes Grimlock, Ferocious King.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{2}")));

        // Grimlock, Ferocious King
        this.getRightHalfCard().setPT(8, 8);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // {2}: Grimlock, Ferocious King becomes Grimlock, Dinobot Leader.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{2}")));
    }

    private GrimlockDinobotLeader(final GrimlockDinobotLeader card) {
        super(card);
    }

    @Override
    public GrimlockDinobotLeader copy() {
        return new GrimlockDinobotLeader(this);
    }
}

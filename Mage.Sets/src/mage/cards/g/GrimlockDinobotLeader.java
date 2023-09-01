package mage.cards.g;

import mage.MageObject;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
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
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;

import java.util.UUID;


/**
 * @author Saga
 */
public final class GrimlockDinobotLeader extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Dinosaurs, Vehicles and other Transformers creatures");

    static {
        filter.add(GrimlockDinobotLeaderPredicate.instance);
    }

    public GrimlockDinobotLeader(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.AUTOBOT}, "{1}{R}{G}{W}",
                "Grimlock, Ferocious King",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.DINOSAUR}, "GRW"
        );
        this.getLeftHalfCard().setPT(4, 4);
        this.getRightHalfCard().setPT(8, 8);

        // Dinosaurs, Vehicles and other Transformers creatures you control get +2/+0.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 0, Duration.WhileOnBattlefield, filter
        )));

        // {2}: Grimlock, Dinobot Leader becomes Grimlock, Ferocious King.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(
                new TransformSourceEffect().setText("{this} becomes Grimlock, Ferocious King"), new GenericManaCost(2)
        ));

        // Grimlock, Ferocious King
        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // {2}: Grimlock, Ferocious King becomes Grimlock, Dinobot Leader.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(
                new TransformSourceEffect().setText("{this} becomes Grimlock, Dinobot Leader"), new GenericManaCost(2)
        ));
    }

    private GrimlockDinobotLeader(final GrimlockDinobotLeader card) {
        super(card);
    }

    @Override
    public GrimlockDinobotLeader copy() {
        return new GrimlockDinobotLeader(this);
    }
}

enum GrimlockDinobotLeaderPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return (!input.getObject().hasSubtype(SubType.DINOSAUR, game) && !input.getObject().hasSubtype(SubType.VEHICLE, game))
                || (AnotherPredicate.instance.apply(input, game) && input.getObject().getExpansionSetCode().equals("BOT"));
    }
}

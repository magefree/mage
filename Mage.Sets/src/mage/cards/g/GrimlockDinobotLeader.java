
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.TransformedPredicate;


/**
 *
 * @author Saga
 */
public final class GrimlockDinobotLeader extends CardImpl{
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dinosaurs and Vehicles");
    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.DINOSAUR), new SubtypePredicate(SubType.VEHICLE)));
    }
    
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Transformers creatures");
    static {
        filter2.add(Predicates.not(new SubtypePredicate(SubType.DINOSAUR))); 
        filter2.add(Predicates.not(new SubtypePredicate(SubType.VEHICLE))); 
        filter2.add(Predicates.or(new AbilityPredicate(TransformAbility.class), new TransformedPredicate()));
    }
    
    public GrimlockDinobotLeader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT,CardType.CREATURE}, "{1}{R}{G}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AUTOBOT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        
        this.transformable = true;
        this.secondSideCardClazz = GrimlockFerociousKing.class;

        // Dinosaurs, Vehicles and other Transformers creatures you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield, filter, false)));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield, filter2, true)));
        
        // {2}: Grimlock, Dinobot Leader becomes Grimlock, Ferocious King.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(true), new ManaCostsImpl("{2}")));
    }

    public GrimlockDinobotLeader(final GrimlockDinobotLeader card) {
        super(card);
    }

    @Override
    public GrimlockDinobotLeader copy() {
        return new GrimlockDinobotLeader(this);
    }
    
}

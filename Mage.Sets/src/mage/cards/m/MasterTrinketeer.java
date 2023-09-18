package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.ServoToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MasterTrinketeer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Servos and Thopters");

    static {
        filter.add(Predicates.or(
                SubType.SERVO.getPredicate(),
                SubType.THOPTER.getPredicate()
        ));
    }

    public MasterTrinketeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Servo and Thopter creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        )));

        // {3}{W}: Create a 1/1 colorless Servo artifact creature token.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new ServoToken(), 1), new ManaCostsImpl<>("{3}{W}")
        ));
    }

    private MasterTrinketeer(final MasterTrinketeer card) {
        super(card);
    }

    @Override
    public MasterTrinketeer copy() {
        return new MasterTrinketeer(this);
    }
}


package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.DinosaurToken;

/**
 *
 * @author TheElk801
 */
public final class ThunderingSpineback extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Dinosaurs");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
    }

    public ThunderingSpineback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Other Dinosaurs you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // {5}{G}: Create a 3/3 green Dinosaur creature token with trample.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new DinosaurToken()), new ManaCostsImpl<>("{5}{G}")));
    }

    private ThunderingSpineback(final ThunderingSpineback card) {
        super(card);
    }

    @Override
    public ThunderingSpineback copy() {
        return new ThunderingSpineback(this);
    }
}

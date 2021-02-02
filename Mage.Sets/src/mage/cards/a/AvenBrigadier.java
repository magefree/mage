
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class AvenBrigadier extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("Bird creatures");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("Soldier creatures");

    static {
        filter1.add(SubType.BIRD.getPredicate());
        filter2.add(SubType.SOLDIER.getPredicate());
    }

    public AvenBrigadier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}{W}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Other Bird creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter1, true)));
        // Other Soldier creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter2, true)));
    }

    private AvenBrigadier(final AvenBrigadier card) {
        super(card);
    }

    @Override
    public AvenBrigadier copy() {
        return new AvenBrigadier(this);
    }
}

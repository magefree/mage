
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author Loki
 */
public final class VeteranSwordsmith extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Soldier");

    static {
        filter.add(SubType.SOLDIER.getPredicate());
    }

    public VeteranSwordsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield, filter, true)));
    }

    private VeteranSwordsmith(final VeteranSwordsmith card) {
        super(card);
    }

    @Override
    public VeteranSwordsmith copy() {
        return new VeteranSwordsmith(this);
    }
}

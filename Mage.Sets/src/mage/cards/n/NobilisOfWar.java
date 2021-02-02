
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author North
 */
public final class NobilisOfWar extends CardImpl {

    public NobilisOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R/W}{R/W}{R/W}{R/W}{R/W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Attacking creatures you control get +2/+0.
        BoostControlledEffect boostEffect = new BoostControlledEffect(2, 0, Duration.WhileOnBattlefield, new FilterAttackingCreature("Attacking creatures"), false);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, boostEffect));
    }

    private NobilisOfWar(final NobilisOfWar card) {
        super(card);
    }

    @Override
    public NobilisOfWar copy() {
        return new NobilisOfWar(this);
    }
}

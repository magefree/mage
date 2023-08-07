

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class EleshNornGrandCenobite extends CardImpl {
    
    public EleshNornGrandCenobite (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Other creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, true)));

        // Creatures your opponents control get -2/-2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostOpponentsEffect(-2, -2, Duration.WhileOnBattlefield)));
    }

    public EleshNornGrandCenobite (final EleshNornGrandCenobite card) {
        super(card);
    }

    @Override
    public EleshNornGrandCenobite copy() {
        return new EleshNornGrandCenobite(this);
    }

}

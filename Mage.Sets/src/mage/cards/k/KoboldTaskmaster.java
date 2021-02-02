
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class KoboldTaskmaster extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Kobold creatures you control");
    
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.KOBOLD.getPredicate());
    }

    public KoboldTaskmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.KOBOLD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Other Kobold creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 0, Duration.WhileOnBattlefield, filter, true)));
        
    }

    private KoboldTaskmaster(final KoboldTaskmaster card) {
        super(card);
    }

    @Override
    public KoboldTaskmaster copy() {
        return new KoboldTaskmaster(this);
    }
}

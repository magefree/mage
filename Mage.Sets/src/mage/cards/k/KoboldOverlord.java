
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author ilcartographer
 */
public final class KoboldOverlord extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Kobold creatures");

    static {
        filter.add(SubType.KOBOLD.getPredicate());
    }
    
    public KoboldOverlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.KOBOLD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Other Kobold creatures you control have first strike.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(FirstStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)));
    }

    private KoboldOverlord(final KoboldOverlord card) {
        super(card);
    }

    @Override
    public KoboldOverlord copy() {
        return new KoboldOverlord(this);
    }
}

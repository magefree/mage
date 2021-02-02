
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class BlurSliver extends CardImpl {

    public BlurSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sliver creatures you control have haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, 
                new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                        new FilterControlledCreaturePermanent(SubType.SLIVER, "Sliver creatures you control"))));
        
    }

    private BlurSliver(final BlurSliver card) {
        super(card);
    }

    @Override
    public BlurSliver copy() {
        return new BlurSliver(this);
    }
}

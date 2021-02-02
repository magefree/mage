
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class BoonSatyr extends CardImpl {

    public BoonSatyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.SATYR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Bestow {3}{G}{G}
        this.addAbility(new BestowAbility(this, "{3}{G}{G}"));
        // Enchanted creature gets +4/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(4,2, Duration.WhileOnBattlefield)));
    }

    private BoonSatyr(final BoonSatyr card) {
        super(card);
    }

    @Override
    public BoonSatyr copy() {
        return new BoonSatyr(this);
    }
}

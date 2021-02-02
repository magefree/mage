
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class NyxbornShieldmate extends CardImpl {

    public NyxbornShieldmate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Bestow {2}{W}
        this.addAbility(new BestowAbility(this, "{2}{W}"));
        // Enchanted creature gets +1/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1,2, Duration.WhileOnBattlefield)));
    }

    private NyxbornShieldmate(final NyxbornShieldmate card) {
        super(card);
    }

    @Override
    public NyxbornShieldmate copy() {
        return new NyxbornShieldmate(this);
    }
}


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
public final class NyxbornTriton extends CardImpl {

    public NyxbornTriton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Bestow {4}{U}
        this.addAbility(new BestowAbility(this, "{4}{U}"));
        // Enchanted creature gets +2/+3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2,3, Duration.WhileOnBattlefield)));        
    }

    private NyxbornTriton(final NyxbornTriton card) {
        super(card);
    }

    @Override
    public NyxbornTriton copy() {
        return new NyxbornTriton(this);
    }
}

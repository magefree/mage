
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author Loki
 */
public final class YavimayaEnchantress extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("enchantment on the battlefield");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public YavimayaEnchantress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter), new PermanentsOnBattlefieldCount(filter), Duration.WhileOnBattlefield)));
    }

    private YavimayaEnchantress(final YavimayaEnchantress card) {
        super(card);
    }

    @Override
    public YavimayaEnchantress copy() {
        return new YavimayaEnchantress(this);
    }
}

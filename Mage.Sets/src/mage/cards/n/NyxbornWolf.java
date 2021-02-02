
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
public final class NyxbornWolf extends CardImpl {

    public NyxbornWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Bestow {4}{G}
        this.addAbility(new BestowAbility(this, "{4}{G}"));
        // Enchanted creature gets +3/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3,1, Duration.WhileOnBattlefield)));
    }

    private NyxbornWolf(final NyxbornWolf card) {
        super(card);
    }

    @Override
    public NyxbornWolf copy() {
        return new NyxbornWolf(this);
    }
}

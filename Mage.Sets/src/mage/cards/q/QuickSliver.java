
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author cbt33, Loki (Shimmer Myr)
 */
public final class QuickSliver extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("Sliver spells");
    static {
        filter.add(SubType.SLIVER.getPredicate());
    }

    public QuickSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Any player may play Sliver cards as though they had flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter, true)));
    }

    private QuickSliver(final QuickSliver card) {
        super(card);
    }

    @Override
    public QuickSliver copy() {
        return new QuickSliver(this);
    }
}

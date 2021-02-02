
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author Loki
 */
public final class WingedSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("All sliver creatures");

    static {
        filter.add(SubType.SLIVER.getPredicate());
    }

    public WingedSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield, filter, false)));
    }

    private WingedSliver(final WingedSliver card) {
        super(card);
    }

    @Override
    public WingedSliver copy() {
        return new WingedSliver(this);
    }
}

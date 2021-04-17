
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class SidewinderSliver extends CardImpl {

    static final private FilterCreaturePermanent filter = new FilterCreaturePermanent("All Sliver creatures");

    static {
        filter.add(SubType.SLIVER.getPredicate());
    }

    public SidewinderSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Sliver creatures have flanking.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(new FlankingAbility(), Duration.WhileOnBattlefield, filter, false)
                        .setText("all Sliver creatures have flanking")
        ));
    }

    private SidewinderSliver(final SidewinderSliver card) {
        super(card);
    }

    @Override
    public SidewinderSliver copy() {
        return new SidewinderSliver(this);
    }
}

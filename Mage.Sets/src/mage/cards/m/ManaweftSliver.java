
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ManaweftSliver extends CardImpl {

    public static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SLIVER, "Sliver creatures");

    public ManaweftSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sliver creatures you control have "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(
                new AnyColorManaAbility(),
                Duration.WhileOnBattlefield,
                filter
        )));
    }

    private ManaweftSliver(final ManaweftSliver card) {
        super(card);
    }

    @Override
    public ManaweftSliver copy() {
        return new ManaweftSliver(this);
    }
}


package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterEnchantmentPermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HelmOfTheGods extends CardImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("enchantment you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public HelmOfTheGods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1 for each enchantment you control.
        PermanentsOnBattlefieldCount countEnchantments = new PermanentsOnBattlefieldCount(filter);
        Effect effect = new BoostEquippedEffect(countEnchantments, countEnchantments);
        effect.setText("Equipped creature gets +1/+1 for each enchantment you control");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Equip {1}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    private HelmOfTheGods(final HelmOfTheGods card) {
        super(card);
    }

    @Override
    public HelmOfTheGods copy() {
        return new HelmOfTheGods(this);
    }
}

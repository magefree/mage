package mage.cards.n;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.LivingWeaponAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Nettlecyst extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactOrEnchantmentPermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Artifacts and enchantments you control", xValue);

    public Nettlecyst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Living weapon
        this.addAbility(new LivingWeaponAbility());

        // Equipped creature gets +1/+1 for each artifact and/or enchantment you control.
        this.addAbility(new SimpleStaticAbility(
                new BoostEquippedEffect(xValue, xValue)
                        .setText("equipped creature gets +1/+1 for each artifact and/or enchantment you control")
        ).addHint(hint));

        // Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private Nettlecyst(final Nettlecyst card) {
        super(card);
    }

    @Override
    public Nettlecyst copy() {
        return new Nettlecyst(this);
    }
}

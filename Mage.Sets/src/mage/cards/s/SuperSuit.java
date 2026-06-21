package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SuperSuit extends CardImpl {

    public SuperSuit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When this Equipment enters, attach it to target creature you control. Untap that creature.
        Ability ability = new EntersBattlefieldAttachToTarget();
        ability.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        this.addAbility(ability);

        // Equipped creature gets +1/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 2)));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private SuperSuit(final SuperSuit card) {
        super(card);
    }

    @Override
    public SuperSuit copy() {
        return new SuperSuit(this);
    }
}

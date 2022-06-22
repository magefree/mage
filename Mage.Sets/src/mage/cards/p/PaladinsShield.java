package mage.cards.p;

import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class PaladinsShield extends CardImpl {

    public PaladinsShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Paladin's Shield enters the battlefield, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +0/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(0, 2)));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private PaladinsShield(final PaladinsShield card) {
        super(card);
    }

    @Override
    public PaladinsShield copy() {
        return new PaladinsShield(this);
    }
}

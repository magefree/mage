package mage.cards.m;

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
 * @author TheElk801
 */
public final class MalametScythe extends CardImpl {

    public MalametScythe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Malamet Scythe enters the battlefield, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 2)));

        // Equip {4}
        this.addAbility(new EquipAbility(4));
    }

    private MalametScythe(final MalametScythe card) {
        super(card);
    }

    @Override
    public MalametScythe copy() {
        return new MalametScythe(this);
    }
}

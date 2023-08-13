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
public final class MirranBanesplitter extends CardImpl {

    public MirranBanesplitter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Mirran Banesplitter enters the battlefield, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private MirranBanesplitter(final MirranBanesplitter card) {
        super(card);
    }

    @Override
    public MirranBanesplitter copy() {
        return new MirranBanesplitter(this);
    }
}
// for you

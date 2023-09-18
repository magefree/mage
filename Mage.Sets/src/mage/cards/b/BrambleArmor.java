package mage.cards.b;

import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrambleArmor extends CardImpl {

    public BrambleArmor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Bramble Armor enters the battlefield, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Equip {4}
        this.addAbility(new EquipAbility(4));
    }

    private BrambleArmor(final BrambleArmor card) {
        super(card);
    }

    @Override
    public BrambleArmor copy() {
        return new BrambleArmor(this);
    }
}

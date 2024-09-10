package mage.cards.s;

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
public final class SovereignsMacuahuitl extends CardImpl {

    public SovereignsMacuahuitl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.subtype.add(SubType.EQUIPMENT);
        this.nightCard = true;
        this.color.setRed(true);

        // When Sovereign's Macuahuitl enters the battlefield, attach it to target creature you control.
        this.addAbility(new EntersBattlefieldAttachToTarget());

        // Equipped creature gets +2/+0.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private SovereignsMacuahuitl(final SovereignsMacuahuitl card) {
        super(card);
    }

    @Override
    public SovereignsMacuahuitl copy() {
        return new SovereignsMacuahuitl(this);
    }
}

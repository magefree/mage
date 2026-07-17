package mage.cards.h;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HardWonJitte extends CardImpl {

    public HardWonJitte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        )));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private HardWonJitte(final HardWonJitte card) {
        super(card);
    }

    @Override
    public HardWonJitte copy() {
        return new HardWonJitte(this);
    }
}

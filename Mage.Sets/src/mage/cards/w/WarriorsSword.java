package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.JobSelectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarriorsSword extends CardImpl {

    public WarriorsSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +3/+2 and is a Warrior in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 2));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.WARRIOR, AttachmentType.EQUIPMENT
        ).setText("and is a Warrior in addition to its other types"));
        this.addAbility(ability);

        // Equip {5}
        this.addAbility(new EquipAbility(5));
    }

    private WarriorsSword(final WarriorsSword card) {
        super(card);
    }

    @Override
    public WarriorsSword copy() {
        return new WarriorsSword(this);
    }
}

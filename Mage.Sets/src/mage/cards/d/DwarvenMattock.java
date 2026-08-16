package mage.cards.d;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAttachToTarget;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DwarvenMattock extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DWARF);

    public DwarvenMattock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, attach it to target Dwarf you control.
        this.addAbility(new EntersBattlefieldAttachToTarget(filter));

        // Equipped creature gets +2/+2 and has ward {1}.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
            new WardAbility(new GenericManaCost(1)), AttachmentType.EQUIPMENT
        ).setText("and has ward {1}"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private DwarvenMattock(final DwarvenMattock card) {
        super(card);
    }

    @Override
    public DwarvenMattock copy() {
        return new DwarvenMattock(this);
    }
}

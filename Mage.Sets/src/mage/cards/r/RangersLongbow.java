package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RangersLongbow extends CardImpl {

    public RangersLongbow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+1 and has reach.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                ReachAbility.getInstance(), AttachmentType.AURA
        ).setText("and has reach"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private RangersLongbow(final RangersLongbow card) {
        super(card);
    }

    @Override
    public RangersLongbow copy() {
        return new RangersLongbow(this);
    }
}

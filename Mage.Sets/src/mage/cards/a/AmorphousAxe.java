package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.ChangelingAbility;
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
public final class AmorphousAxe extends CardImpl {

    public AmorphousAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+0 and is every creature type.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                ChangelingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and is every creature type"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private AmorphousAxe(final AmorphousAxe card) {
        super(card);
    }

    @Override
    public AmorphousAxe copy() {
        return new AmorphousAxe(this);
    }
}

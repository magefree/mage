package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ColossusHammer extends CardImpl {

    public ColossusHammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +10/+10 and loses flying.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(10, 10));
        ability.addEffect(new LoseAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and loses flying"));
        this.addAbility(ability);

        // Equip {8}
        this.addAbility(new EquipAbility(8));
    }

    private ColossusHammer(final ColossusHammer card) {
        super(card);
    }

    @Override
    public ColossusHammer copy() {
        return new ColossusHammer(this);
    }
}

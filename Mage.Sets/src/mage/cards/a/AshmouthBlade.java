package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AshmouthBlade extends CardImpl {

    public AshmouthBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");
        this.subtype.add(SubType.EQUIPMENT);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Equipped creature gets +3/+3 and has first strike.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 3));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has first strike"));
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private AshmouthBlade(final AshmouthBlade card) {
        super(card);
    }

    @Override
    public AshmouthBlade copy() {
        return new AshmouthBlade(this);
    }
}

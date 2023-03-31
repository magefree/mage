package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RamosianGreatsword extends CardImpl {

    public RamosianGreatsword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Equipped creature gets +3/+1 and has trample.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has trample"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private RamosianGreatsword(final RamosianGreatsword card) {
        super(card);
    }

    @Override
    public RamosianGreatsword copy() {
        return new RamosianGreatsword(this);
    }
}

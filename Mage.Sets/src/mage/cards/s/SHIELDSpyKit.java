package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.UntapAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SHIELDSpyKit extends CardImpl {

    public SHIELDSpyKit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Whenever equipped creature attacks alone, untap it and scry 1.
        Ability ability = new AttacksAloneAttachedTriggeredAbility(
            new UntapAttachedEffect(AttachmentType.EQUIPMENT, "it").setText("untap it")
        );
        ability.addEffect(new ScryEffect(1).concatBy("and"));
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private SHIELDSpyKit(final SHIELDSpyKit card) {
        super(card);
    }

    @Override
    public SHIELDSpyKit copy() {
        return new SHIELDSpyKit(this);
    }
}

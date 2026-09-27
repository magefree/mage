package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.AttachmentType;
import mage.constants.SubType;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HuntersAxe extends CardImpl {

    public HuntersAxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{G}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and has "Whenever this creature attacks, it gains your choice of trample or deathtouch until end of turn."
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        TriggeredAbility gainedAbility = new AttacksTriggeredAbility(new GainsChoiceOfAbilitiesEffect(
            GainsChoiceOfAbilitiesEffect.TargetType.Source,
            TrampleAbility.getInstance(), DeathtouchAbility.getInstance()
        ));
        ability.addEffect(new GainAbilityAttachedEffect(gainedAbility, AttachmentType.EQUIPMENT)
            .setText("and has \"Whenever this creature attacks, it gains your choice of trample or deathtouch until end of turn.\""));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private HuntersAxe(final HuntersAxe card) {
        super(card);
    }

    @Override
    public HuntersAxe copy() {
        return new HuntersAxe(this);
    }
}

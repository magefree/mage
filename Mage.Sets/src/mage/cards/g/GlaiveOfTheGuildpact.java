package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.GateYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.common.GateYouControlHint;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlaiveOfTheGuildpact extends CardImpl {

    public GlaiveOfTheGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 for each Gate you control and has vigilance and menace.
        Ability ability = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostEquippedEffect(
                        GateYouControlCount.instance,
                        new StaticValue(0)
                )
        );
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText("and has vigilance"));
        ability.addEffect(new GainAbilityAttachedEffect(
                new MenaceAbility(), AttachmentType.EQUIPMENT
        ).setText("and menace"));
        ability.addHint(GateYouControlHint.instance);
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    public GlaiveOfTheGuildpact(final GlaiveOfTheGuildpact card) {
        super(card);
    }

    @Override
    public GlaiveOfTheGuildpact copy() {
        return new GlaiveOfTheGuildpact(this);
    }
}

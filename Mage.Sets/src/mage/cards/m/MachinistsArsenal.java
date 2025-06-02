package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
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
public final class MachinistsArsenal extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(ArtifactYouControlCount.instance, 2);

    public MachinistsArsenal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +2/+2 for each artifact you control and is an Artificer in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(xValue, xValue)
                .setText("equipped creature gets +2/+2 for each artifact you control"));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.ARTIFICER, AttachmentType.EQUIPMENT
        ).setText("and is an Artificer in addition to its other types"));
        this.addAbility(ability.addHint(ArtifactYouControlHint.instance));

        // Machina -- Equip {4}
        this.addAbility(new EquipAbility(4).withFlavorWord("Machina"));
    }

    private MachinistsArsenal(final MachinistsArsenal card) {
        super(card);
    }

    @Override
    public MachinistsArsenal copy() {
        return new MachinistsArsenal(this);
    }
}

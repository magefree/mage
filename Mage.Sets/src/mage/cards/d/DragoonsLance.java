package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
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
public final class DragoonsLance extends CardImpl {

    public DragoonsLance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Job select
        this.addAbility(new JobSelectAbility());

        // Equipped creature gets +1/+0 and is a Knight in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.KNIGHT, AttachmentType.EQUIPMENT
        ).setText("and is a Knight in addition to its other types"));
        this.addAbility(ability);

        // During your turn, equipped creature has flying.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.EQUIPMENT),
                MyTurnCondition.instance, "during your turn, equipped creature has flying"
        )));

        // Gae Bolg -- Equip {4}
        this.addAbility(new EquipAbility(4).withFlavorWord("Gae Bolg"));
    }

    private DragoonsLance(final DragoonsLance card) {
        super(card);
    }

    @Override
    public DragoonsLance copy() {
        return new DragoonsLance(this);
    }
}

package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.FirstCombatPhaseCondition;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
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
public final class GenjiGlove extends CardImpl {

    public GenjiGlove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                DoubleStrikeAbility.getInstance(), AttachmentType.EQUIPMENT
        )));

        // Whenever equipped creature attacks, if it's the first combat phase of the turn, untap it. After this phase, there is an additional combat phase.
        Ability ability = new AttacksAttachedTriggeredAbility(new UntapAttachedEffect().setText("untap it"))
                .withInterveningIf(FirstCombatPhaseCondition.instance);
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);

        // Equip {3}
        this.addAbility(new EquipAbility(3));
    }

    private GenjiGlove(final GenjiGlove card) {
        super(card);
    }

    @Override
    public GenjiGlove copy() {
        return new GenjiGlove(this);
    }
}

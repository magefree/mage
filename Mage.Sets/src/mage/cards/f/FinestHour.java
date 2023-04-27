package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.condition.common.FirstCombatPhaseCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author awjackson
 */
public final class FinestHour extends CardImpl {

    public FinestHour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}{W}{U}");

        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        this.addAbility(new ExaltedAbility());

        // Whenever a creature you control attacks alone, if it's the first combat phase of the turn, untap that
        // creature. After this phase, there is an additional combat phase.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksAloneControlledTriggeredAbility(new UntapTargetEffect("untap that creature"), true, false),
                FirstCombatPhaseCondition.instance,
                "Whenever a creature you control attacks alone, if it's the first combat phase of the turn, " +
                "untap that creature. After this phase, there is an additional combat phase."
        );
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);
    }

    private FinestHour(final FinestHour card) {
        super(card);
    }

    @Override
    public FinestHour copy() {
        return new FinestHour(this);
    }
}

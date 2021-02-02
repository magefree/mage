
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.TargetPlayer;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

/**
 *
 * @author Styxo
 */
public final class DarkApprenticeship extends CardImpl {

    public DarkApprenticeship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // <i>Hate</i> &mdash; At the beggining of your end step, if an opponent lost life from source other than combat damage this turn, Dark Apprenticeship deals 2 damage to target player.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new DamageTargetEffect(2), TargetController.YOU, false),
                HateCondition.instance,
                "<i>Hate</i> &mdash; At the beggining of your end step, if an opponent lost life from source other than combat damage this turn, Dark Apprenticeship deals 2 damage to target player.");
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability, new LifeLossOtherFromCombatWatcher());
    }

    private DarkApprenticeship(final DarkApprenticeship card) {
        super(card);
    }

    @Override
    public DarkApprenticeship copy() {
        return new DarkApprenticeship(this);
    }
}

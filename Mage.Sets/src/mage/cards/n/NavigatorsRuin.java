
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetOpponent;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author TheElk801
 */
public final class NavigatorsRuin extends CardImpl {

    public NavigatorsRuin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Raid - At the beginning of your end step, if you attacked with a creature this turm, target opponent puts the top four cards of their library into their graveyard.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(new PutLibraryIntoGraveTargetEffect(4), TargetController.YOU, false),
                RaidCondition.instance,
                "<i>Raid</i> &mdash; At the beginning of your end step, if you attacked with a creature this turn, target opponent puts the top four cards of their library into their graveyard.");
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    public NavigatorsRuin(final NavigatorsRuin card) {
        super(card);
    }

    @Override
    public NavigatorsRuin copy() {
        return new NavigatorsRuin(this);
    }
}


package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.RoyalGuardToken;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

/**
 *
 * @author Styxo
 */
public final class IronFistOfTheEmpire extends CardImpl {

    public IronFistOfTheEmpire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{B}{R}");

        // <i>Hate</i> &mdash; At the beggining of each end step, if opponent lost life from a source other than combat damage this turn, you gain 2 life and create a 2/2 red Soldier creature token with first strike name Royal Guard.
        TriggeredAbility triggeredAbility = new BeginningOfEndStepTriggeredAbility(new GainLifeEffect(2), TargetController.ANY, false);
        triggeredAbility.addEffect(new CreateTokenEffect(new RoyalGuardToken()));
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                triggeredAbility,
                HateCondition.instance,
                "<i>Hate</i> &mdash; At the beggining of each end step, if opponent lost life from a source other than combat damage this turn, you gain 1 life and create a 2/2 red Soldier creature token with first strike named Royal Guard.");
        this.addAbility(ability, new LifeLossOtherFromCombatWatcher());
    }

    private IronFistOfTheEmpire(final IronFistOfTheEmpire card) {
        super(card);
    }

    @Override
    public IronFistOfTheEmpire copy() {
        return new IronFistOfTheEmpire(this);
    }
}

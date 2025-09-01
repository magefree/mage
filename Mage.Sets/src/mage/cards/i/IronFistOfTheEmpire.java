package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.RoyalGuardToken;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class IronFistOfTheEmpire extends CardImpl {

    public IronFistOfTheEmpire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{B}{R}");

        // <i>Hate</i> &mdash; At the beggining of each end step, if opponent lost life from a source other than combat damage this turn, you gain 2 life and create a 2/2 red Soldier creature token with first strike name Royal Guard.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new GainLifeEffect(2), false
        ).withInterveningIf(HateCondition.instance);
        ability.addEffect(new CreateTokenEffect(new RoyalGuardToken()).concatBy("and"));
        this.addAbility(ability.setAbilityWord(AbilityWord.HATE), new LifeLossOtherFromCombatWatcher());
    }

    private IronFistOfTheEmpire(final IronFistOfTheEmpire card) {
        super(card);
    }

    @Override
    public IronFistOfTheEmpire copy() {
        return new IronFistOfTheEmpire(this);
    }
}

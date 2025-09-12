package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.target.TargetPlayer;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class DarkApprenticeship extends CardImpl {

    public DarkApprenticeship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // <i>Hate</i> &mdash; At the beggining of your end step, if an opponent lost life from source other than combat damage this turn, Dark Apprenticeship deals 2 damage to target player.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new DamageTargetEffect(2))
                .withInterveningIf(HateCondition.instance);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability.setAbilityWord(AbilityWord.HATE), new LifeLossOtherFromCombatWatcher());
    }

    private DarkApprenticeship(final DarkApprenticeship card) {
        super(card);
    }

    @Override
    public DarkApprenticeship copy() {
        return new DarkApprenticeship(this);
    }
}

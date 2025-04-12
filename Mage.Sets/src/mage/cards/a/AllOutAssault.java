package mage.cards.a;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.WhenYouAttackDelayedTriggeredAbility;
import mage.abilities.condition.common.IsMainPhaseCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AddCombatAndMainPhaseEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author androosss
 */
public final class AllOutAssault extends CardImpl {

    public AllOutAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{W}{B}");

        // Creatures you control get +1/+1 and have deathtouch.
        Ability anthemAbility = new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityControlledEffect(DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("and have deathtouch");
        anthemAbility.addEffect(effect);
        this.addAbility(anthemAbility);

        // When this enchantment enters, if it's your main phase, there is an additional combat phase after this phase followed by an additional main phase. When you next attack this turn, untap each creature you control.
        TriggeredAbility extraCombatAbility = new EntersBattlefieldTriggeredAbility(new AddCombatAndMainPhaseEffect().setText("there is an additional combat phase after this phase followed by an additional main phase")).setTriggerPhrase("When this enchantment enters, ");
        extraCombatAbility.addEffect(new CreateDelayedTriggeredAbilityEffect(new WhenYouAttackDelayedTriggeredAbility(
                new UntapAllControllerEffect(
                        StaticFilters.FILTER_CONTROLLED_CREATURE, "untap each creature you control"), Duration.EndOfTurn, true)));
        this.addAbility(extraCombatAbility.withInterveningIf(IsMainPhaseCondition.YOUR));

    }

    private AllOutAssault(final AllOutAssault card) {
        super(card);
    }

    @Override
    public AllOutAssault copy() {
        return new AllOutAssault(this);
    }
}

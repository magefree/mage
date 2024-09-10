package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateOncePerGameActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class TouchOfVitae extends CardImpl {

    public TouchOfVitae(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Until end of turn, target creature gains haste and "{0}: Untap this creature. Activate only once."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setText("until end of turn, target creature gains haste"));

        Ability untapAbility = new ActivateOncePerGameActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new GenericManaCost(0), TimingRule.INSTANT);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(untapAbility, Duration.EndOfTurn)
                .concatBy("and")
                .setText("\"{0}: Untap this creature. Activate only once.\""));

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false)
                .concatBy("<br>"));
    }

    private TouchOfVitae(final TouchOfVitae card) {
        super(card);
    }

    @Override
    public TouchOfVitae copy() {
        return new TouchOfVitae(this);
    }
}

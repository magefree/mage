package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AssimilateTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class DeathIsIrrelevant extends CardImpl {

    public DeathIsIrrelevant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{B}{B}");

        // Artifact creatures you control have indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));

        // At the beginning of your end step, assimilate target creature card from a graveyard.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new AssimilateTargetEffect());
        ability.addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE_A_GRAVEYARD));
        this.addAbility(ability);
    }

    private DeathIsIrrelevant(final DeathIsIrrelevant card) {
        super(card);
    }

    @Override
    public DeathIsIrrelevant copy() {
        return new DeathIsIrrelevant(this);
    }
}

package mage.cards.a;

import java.util.UUID;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class ArtifistAcumen extends CardImpl {

    public ArtifistAcumen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

       // Creatures you control gain first strike until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityAllEffect(
            FirstStrikeAbility.getInstance(), Duration.EndOfTurn,
            StaticFilters.FILTER_CONTROLLED_CREATURES,
            "Creatures you control gain first strike until end of turn"
        ));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private ArtifistAcumen(final ArtifistAcumen card) {
        super(card);
    }

    @Override
    public ArtifistAcumen copy() {
        return new ArtifistAcumen(this);
    }
}

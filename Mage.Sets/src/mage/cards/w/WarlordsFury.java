
package mage.cards.w;

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
 * @author TheElk801
 */
public final class WarlordsFury extends CardImpl {

    public WarlordsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Creatures you control gain first strike until end of turn.
        getSpellAbility().addEffect(new GainAbilityAllEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES, "Creatures you control gain first strike until end of turn.<br>"));

        // Draw a card.
        getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));

    }

    private WarlordsFury(final WarlordsFury card) {
        super(card);
    }

    @Override
    public WarlordsFury copy() {
        return new WarlordsFury(this);
    }
}

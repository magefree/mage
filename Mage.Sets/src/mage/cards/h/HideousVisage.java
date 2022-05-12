
package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class HideousVisage extends CardImpl {

    private static final String rule = "creatures you control gain intimidate until end of turn. " +
            "<i>(Each of those creatures can't be blocked except by artifact creatures and/or " +
            "creatures that share a color with it.)</i>";

    public HideousVisage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                IntimidateAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES).setText(rule));
    }

    private HideousVisage(final HideousVisage card) {
        super(card);
    }

    @Override
    public HideousVisage copy() {
        return new HideousVisage(this);
    }
}

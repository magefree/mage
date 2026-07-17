package mage.cards.s;

import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SozinsComet extends CardImpl {

    public SozinsComet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Each creature you control gains firebending 5 until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                new FirebendingAbility(5), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("each creature you control gains firebending 5 until end of turn"));

        // Foretell {2}{R}
        this.addAbility(new ForetellAbility(this, "{2}{R}"));
    }

    private SozinsComet(final SozinsComet card) {
        super(card);
    }

    @Override
    public SozinsComet copy() {
        return new SozinsComet(this);
    }
}

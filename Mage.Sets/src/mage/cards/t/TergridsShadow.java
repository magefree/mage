package mage.cards.t;

import mage.abilities.effects.common.SacrificeAllEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TergridsShadow extends CardImpl {

    public TergridsShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{B}");

        // Each player sacrifices two creatures.
        this.getSpellAbility().addEffect(new SacrificeAllEffect(
                2, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
        ));

        // Foretell {2}{B}{B}
        this.addAbility(new ForetellAbility(this, "{2}{B}{B}"));
    }

    private TergridsShadow(final TergridsShadow card) {
        super(card);
    }

    @Override
    public TergridsShadow copy() {
        return new TergridsShadow(this);
    }
}

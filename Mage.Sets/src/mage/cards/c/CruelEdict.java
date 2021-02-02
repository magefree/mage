
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public final class CruelEdict extends CardImpl {

    public CruelEdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent sacrifices a creature.
        this.getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "Target opponent"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private CruelEdict(final CruelEdict card) {
        super(card);
    }

    @Override
    public CruelEdict copy() {
        return new CruelEdict(this);
    }
}


package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class DiabolicEdict extends CardImpl {

    public DiabolicEdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target player sacrifices a creature.
        this.getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "Target player"));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private DiabolicEdict(final DiabolicEdict card) {
        super(card);
    }

    @Override
    public DiabolicEdict copy() {
        return new DiabolicEdict(this);
    }
}

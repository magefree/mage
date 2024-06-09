package mage.cards.t;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TectonicHazard extends CardImpl {

    public TectonicHazard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Tectonic Hazard deals 1 damage to each opponent and each creature they control.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(1, TargetController.OPPONENT));
        this.getSpellAbility().addEffect(new DamageAllEffect(
                1, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ).setText("and each creature they control"));
    }

    private TectonicHazard(final TectonicHazard card) {
        super(card);
    }

    @Override
    public TectonicHazard copy() {
        return new TectonicHazard(this);
    }
}

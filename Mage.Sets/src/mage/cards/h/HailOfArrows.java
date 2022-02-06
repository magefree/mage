package mage.cards.h;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author LevelX2
 */
public final class HailOfArrows extends CardImpl {

    public HailOfArrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{W}");

        // Hail of Arrows deals X damage divided as you choose among any number of target attacking creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(ManacostVariableValue.REGULAR, StaticFilters.FILTER_ATTACKING_CREATURES));
    }

    private HailOfArrows(final HailOfArrows card) {
        super(card);
    }

    @Override
    public HailOfArrows copy() {
        return new HailOfArrows(this);
    }
}

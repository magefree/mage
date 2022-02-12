package mage.cards.r;

import java.util.UUID;
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
public final class RoilsRetribution extends CardImpl {

    public RoilsRetribution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{W}{W}");

        // Roil's Retribution deals 5 damage divided as you choose among any number of target attacking or blocking creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(5, StaticFilters.FILTER_ATTACKING_OR_BLOCKING_CREATURES));
    }

    private RoilsRetribution(final RoilsRetribution card) {
        super(card);
    }

    @Override
    public RoilsRetribution copy() {
        return new RoilsRetribution(this);
    }
}

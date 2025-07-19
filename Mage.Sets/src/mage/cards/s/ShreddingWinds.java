
package mage.cards.s;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ShreddingWinds extends CardImpl {

    public ShreddingWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Shredding Winds deals 7 damage to target creature with flying.
        this.getSpellAbility().addEffect(new DamageTargetEffect(7));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
    }

    private ShreddingWinds(final ShreddingWinds card) {
        super(card);
    }

    @Override
    public ShreddingWinds copy() {
        return new ShreddingWinds(this);
    }
}

package mage.cards.b;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroyAllAttachedToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public final class BlastfireBolt extends CardImpl {

    public BlastfireBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{R}");

        // Blastfire Bolt deals 5 damage to target creature.  Destroy all Equipment attached to that creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new DestroyAllAttachedToTargetEffect(StaticFilters.FILTER_PERMANENT_EQUIPMENT, "that creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BlastfireBolt(final BlastfireBolt card) {
        super(card);
    }

    @Override
    public BlastfireBolt copy() {
        return new BlastfireBolt(this);
    }
}

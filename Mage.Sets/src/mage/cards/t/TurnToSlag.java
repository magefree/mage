package mage.cards.t;

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
 * @author awjackson
 */
public final class TurnToSlag extends CardImpl {

    public TurnToSlag (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");

        // Turn To Slag deals 5 damage to target creature. Destroy all Equipment attached to that creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new DestroyAllAttachedToTargetEffect(StaticFilters.FILTER_PERMANENT_EQUIPMENT, "that creature"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public TurnToSlag (final TurnToSlag card) {
        super(card);
    }

    @Override
    public TurnToSlag copy() {
        return new TurnToSlag(this);
    }
}

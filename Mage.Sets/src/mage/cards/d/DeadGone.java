package mage.cards.d;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author dustinconrad
 */
public final class DeadGone extends SplitCard {

    public DeadGone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}", "{2}{R}", SpellAbilityType.SPLIT);

        // Dead
        // Dead deals 2 damage to target creature.
        getLeftHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(2, "Dead"));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Gone
        // Return target creature you don't control to its owner's hand.
        getRightHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private DeadGone(final DeadGone card) {
        super(card);
    }

    @Override
    public DeadGone copy() {
        return new DeadGone(this);
    }
}


package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

public final class FarAway extends SplitCard {

    public FarAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}", "{2}{B}", SpellAbilityType.SPLIT_FUSED);

        // Far
        // Return target creature to its owner's hand.
        getLeftHalfCard().getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Away
        // Target player sacrifices a creature.
        getRightHalfCard().getSpellAbility().addEffect(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "Target player"));
        getRightHalfCard().getSpellAbility().addTarget(new TargetPlayer());

    }

    private FarAway(final FarAway card) {
        super(card);
    }

    @Override
    public FarAway copy() {
        return new FarAway(this);
    }
}

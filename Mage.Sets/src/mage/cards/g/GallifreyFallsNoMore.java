package mage.cards.g;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.DamagedByWatcher;

import java.util.UUID;

/**
 * @author dragonfyre23
 */
public final class GallifreyFallsNoMore extends SplitCard {


    public GallifreyFallsNoMore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}{R}", "{2}{W}", SpellAbilityType.SPLIT_FUSED);

        // Gallifrey Falls
        // Gallifrey Falls deals 4 damage to each creature.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DamageAllEffect(4, new FilterCreaturePermanent()));

        //If a creature dealt damage this way would die this turn, exile it instead.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DealtDamageToCreatureBySourceDies(this.getLeftHalfCard(), Duration.EndOfTurn));
        this.getLeftHalfCard().getSpellAbility().addWatcher(new DamagedByWatcher(false));

        // No More
        // Any number of target creatures you control phase out.
        this.getRightHalfCard().getSpellAbility().addEffect(new PhaseOutTargetEffect());
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE));
    }

    private GallifreyFallsNoMore(final GallifreyFallsNoMore card) {
        super(card);
    }

    @Override
    public GallifreyFallsNoMore copy() {
        return new GallifreyFallsNoMore(this);
    }
}

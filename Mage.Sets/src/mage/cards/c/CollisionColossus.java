package mage.cards.c;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CollisionColossus extends SplitCard {

    public CollisionColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R/G}", "{R}{G}", SpellAbilityType.SPLIT);

        // Collision
        // Collision deals 6 damage to target creature with flying.
        this.getLeftHalfCard().getSpellAbility().addEffect(new DamageTargetEffect(6));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));

        // Colossus
        // Target creature gets +4/+2 and gains trample until end of turn.
        this.getRightHalfCard().getSpellAbility().addEffect(
                new BoostTargetEffect(4, 2, Duration.EndOfTurn)
                        .setText("target creature gets +4/+2")
        );
        this.getRightHalfCard().getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample until end of turn"));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private CollisionColossus(final CollisionColossus card) {
        super(card);
    }

    @Override
    public CollisionColossus copy() {
        return new CollisionColossus(this);
    }
}

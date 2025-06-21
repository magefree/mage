package mage.cards.f;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * As Fall of the Hammer tries to resolve, if only one of the targets is legal,
 * Fall of the Hammer will still resolve but will have no effect: If the first
 * target creature is illegal, it can't deal damage to anything. If the second
 * target creature is illegal, it can't be dealt damage.
 * <p>
 * The amount of damage dealt is based on the first target creature's power as Fall of the Hammer resolves.
 *
 * @author LevelX2
 */
public final class FallOfTheHammer extends CardImpl {

    public FallOfTheHammer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature you control deals damage equal to its power to another target creature.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().withChooseHint("to deal damage").setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).withChooseHint("to receive damage").setTargetTag(2));
    }

    private FallOfTheHammer(final FallOfTheHammer card) {
        super(card);
    }

    @Override
    public FallOfTheHammer copy() {
        return new FallOfTheHammer(this);
    }
}

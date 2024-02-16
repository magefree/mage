package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.Zone;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.command.Emblem;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledPermanent;

/**
 * @author TheElk801
 */
public final class KarnLivingLegacyEmblem extends Emblem {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("an untapped artifact you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    // -7: You get an emblem with "Tap an untapped artifact you control: This emblem deals 1 damage to any target."
    public KarnLivingLegacyEmblem() {
        super("Emblem Karn");
        Ability ability = new SimpleActivatedAbility(
                Zone.COMMAND, new DamageTargetEffect(1, "this emblem"),
                new TapTargetCost(new TargetControlledPermanent(filter))
        );
        ability.addTarget(new TargetAnyTarget());
        this.getAbilities().add(ability);
    }

    private KarnLivingLegacyEmblem(final KarnLivingLegacyEmblem card) {
        super(card);
    }

    @Override
    public KarnLivingLegacyEmblem copy() {
        return new KarnLivingLegacyEmblem(this);
    }
}

package mage.game.command.planes;

import mage.abilities.Ability;
import mage.abilities.common.ChaosEnsuesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Planes;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.game.command.Plane;
import mage.target.TargetPermanent;

/**
 * @author spjspj
 */
public class AkoumPlane extends Plane {

    private static final FilterCard filterCard = new FilterCard("enchantment spells");
    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that isn't enchanted");

    static {
        filter.add(Predicates.not(EnchantedPredicate.instance));
        filterCard.add(CardType.ENCHANTMENT.getPredicate());
    }

    public AkoumPlane() {
        this.setPlaneType(Planes.PLANE_AKOUM);

        // Players may cast enchantment spells as if they had flash
        this.addAbility(new SimpleStaticAbility(
                Zone.COMMAND, new CastAsThoughItHadFlashAllEffect(Duration.Custom, filterCard, true)
        ));

        // Active player can roll the planar die: Whenever you roll {CHAOS}, destroy target creature that isn't enchanted
        Ability ability = new ChaosEnsuesTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AkoumPlane(final AkoumPlane plane) {
        super(plane);
    }

    @Override
    public AkoumPlane copy() {
        return new AkoumPlane(this);
    }
}

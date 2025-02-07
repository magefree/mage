package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author sobiech
 */
public final class AccursedDuneyard extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("Shade, Skeleton, Specter, Spirit, Vampire, Wraith, or Zombie");

    static {
        filter.add(Predicates.or(
                SubType.SHADE.getPredicate(),
                SubType.SKELETON.getPredicate(),
                SubType.SPECTER.getPredicate(),
                SubType.SPIRIT.getPredicate(),
                SubType.VAMPIRE.getPredicate(),
                SubType.WRAITH.getPredicate(),
                SubType.ZOMBIE.getPredicate()
        ));
    }

    public AccursedDuneyard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {2}, {T}: Regenerate target Shade, Skeleton, Specter, Spirit, Vampire, Wraith, or Zombie.
        final Ability ability = new SimpleActivatedAbility(new RegenerateTargetEffect(), new ManaCostsImpl<>("{2}"));
        ability.addTarget(new TargetPermanent(filter));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private AccursedDuneyard(final AccursedDuneyard card) {
        super(card);
    }

    @Override
    public AccursedDuneyard copy() {
        return new AccursedDuneyard(this);
    }
}

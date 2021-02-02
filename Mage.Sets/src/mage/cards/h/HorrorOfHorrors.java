
package mage.cards.h;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class HorrorOfHorrors extends CardImpl {

    private static final FilterControlledPermanent filter1 = new FilterControlledPermanent("a Swamp");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("black creature");

    static {
        filter1.add(SubType.SWAMP.getPredicate());
        filter2.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public HorrorOfHorrors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{B}");

        // Sacrifice a Swamp: Regenerate target black creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(),
            new SacrificeTargetCost(new TargetControlledPermanent(filter1)));
        ability.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability);
    }

    private HorrorOfHorrors(final HorrorOfHorrors card) {
        super(card);
    }

    @Override
    public HorrorOfHorrors copy() {
        return new HorrorOfHorrors(this);
    }
}

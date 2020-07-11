
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class DeathBomb extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");
    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public DeathBomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}");

        // As an additional cost to cast Death Bomb, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, true)));
        // Destroy target nonblack creature. It can't be regenerated. Its controller loses 2 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addEffect(new LoseLifeTargetControllerEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public DeathBomb(final DeathBomb card) {
        super(card);
    }

    @Override
    public DeathBomb copy() {
        return new DeathBomb(this);
    }
}

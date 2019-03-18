
package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SnuffOut extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");
    private static final FilterLandPermanent filterSwamp = new FilterLandPermanent("If you control a Swamp");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filterSwamp.add(new SubtypePredicate(SubType.SWAMP));
    }

    public SnuffOut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}");


        // If you control a Swamp, you may pay 4 life rather than pay Snuff Out's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(
                new PayLifeCost(4),
                new PermanentsOnTheBattlefieldCondition(filterSwamp), null));
        // 
        // Destroy target nonblack creature. It can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public SnuffOut(final SnuffOut card) {
        super(card);
    }

    @Override
    public SnuffOut copy() {
        return new SnuffOut(this);
    }
}

package mage.cards.s;

import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class SnuffOut extends CardImpl {

    private static final FilterLandPermanent filterSwamp = new FilterLandPermanent("If you control a Swamp");

    static {
        filterSwamp.add(SubType.SWAMP.getPredicate());
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
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
    }

    private SnuffOut(final SnuffOut card) {
        super(card);
    }

    @Override
    public SnuffOut copy() {
        return new SnuffOut(this);
    }
}

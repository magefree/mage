package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Temba21
 */
public final class Slaughter extends CardImpl {

    public Slaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}{B}");

        // Buyback-Pay 4 life.
        this.addAbility(new BuybackAbility(new PayLifeCost(4)));
        
        // Destroy target nonblack creature. It can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
    }

    private Slaughter(final Slaughter card) {
        super(card);
    }

    @Override
    public Slaughter copy() {
        return new Slaughter(this);
    }
}

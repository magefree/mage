

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class DemonOfDeathsGate extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("black creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }
    
    public DemonOfDeathsGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{B}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);
        
        // You may pay 6 life and sacrifice three black creatures rather than pay Demon of Death's Gate's mana cost
        AlternativeCostSourceAbility alternateCosts = new AlternativeCostSourceAbility(new PayLifeCost(6));
        alternateCosts.addCost(new SacrificeTargetCost(new TargetControlledPermanent(3, filter)));
        this.addAbility(alternateCosts);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
    }

    private DemonOfDeathsGate(final DemonOfDeathsGate card) {
        super(card);
    }

    @Override
    public DemonOfDeathsGate copy() {
        return new DemonOfDeathsGate(this);
    }

}

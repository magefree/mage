
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class Lithophage extends CardImpl {
    
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("a Mountain");
    
    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }
    
    public Lithophage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // At the beginning of your upkeep, sacrifice Lithophage unless you sacrifice a Mountain.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(filter))));
    }

    private Lithophage(final Lithophage card) {
        super(card);
    }

    @Override
    public Lithophage copy() {
        return new Lithophage(this);
    }
}

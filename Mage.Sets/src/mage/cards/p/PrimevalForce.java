
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class PrimevalForce extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Forests");
    static{
        filter.add(SubType.FOREST.getPredicate());
    }
    
    public PrimevalForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // When Primeval Force enters the battlefield, sacrifice it unless you sacrifice three Forests.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(3, 3, filter, true)))));
    }

    private PrimevalForce(final PrimevalForce card) {
        super(card);
    }

    @Override
    public PrimevalForce copy() {
        return new PrimevalForce(this);
    }
}

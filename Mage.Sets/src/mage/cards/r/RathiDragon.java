
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class RathiDragon extends CardImpl {
    
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("two Mountains");
    
    static{
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
    }

    public RathiDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Rathi Dragon enters the battlefield, sacrifice it unless you sacrifice two Mountains.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, true)))));
    }

    public RathiDragon(final RathiDragon card) {
        super(card);
    }

    @Override
    public RathiDragon copy() {
        return new RathiDragon(this);
    }
}


package mage.cards.m;

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
 * @author fireshoes
 */
public final class MoldDemon extends CardImpl {
    
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("Swamps");
    
    static{
        filter.add(SubType.SWAMP.getPredicate());
    }

    public MoldDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}{B}");
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // When Mold Demon enters the battlefield, sacrifice it unless you sacrifice two Swamps.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, true)))));
    }

    private MoldDemon(final MoldDemon card) {
        super(card);
    }

    @Override
    public MoldDemon copy() {
        return new MoldDemon(this);
    }
}

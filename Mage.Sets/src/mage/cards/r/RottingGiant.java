
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author cbt33
 */
public final class RottingGiant extends CardImpl {

    public RottingGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Rotting Giant attacks or blocks, sacrifice it unless you exile a card from your graveyard.
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard();
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ExileFromGraveCost(target)), false));
    }

    private RottingGiant(final RottingGiant card) {
        super(card);
    }

    @Override
    public RottingGiant copy() {
        return new RottingGiant(this);
    }
}

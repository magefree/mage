
package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandFromGraveyardCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 *
 * @author markedagain
 */
public final class HarvestWurm extends CardImpl {

    public HarvestWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Harvest Wurm enters the battlefield, sacrifice it unless you return a basic land card from your graveyard to your hand.
        TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_BASIC_LAND_A);
        target.withNotTarget(true);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ReturnToHandFromGraveyardCost(target))));
    }

    private HarvestWurm(final HarvestWurm card) {
        super(card);
    }

    @Override
    public HarvestWurm copy() {
        return new HarvestWurm(this);
    }
}


package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;

/**
 *
 * @author LoneFox
 */
public final class FallowWurm extends CardImpl {

    public FallowWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Fallow Wurm enters the battlefield, sacrifice it unless you discard a land card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new DiscardCardCost(new FilterLandCard("a land card")))));
    }

    private FallowWurm(final FallowWurm card) {
        super(card);
    }

    @Override
    public FallowWurm copy() {
        return new FallowWurm(this);
    }
}


package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandCard;

/**
 *
 * @author fireshoes
 */
public final class ThunderingWurm extends CardImpl {

    public ThunderingWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Thundering Wurm enters the battlefield, sacrifice it unless you discard a land card.
        Effect effect = new SacrificeSourceUnlessPaysEffect(new DiscardCardCost(new FilterLandCard("a land card")));
        effect.setText("sacrifice it unless you discard a land card");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect));
    }

    private ThunderingWurm(final ThunderingWurm card) {
        super(card);
    }

    @Override
    public ThunderingWurm copy() {
        return new ThunderingWurm(this);
    }
}

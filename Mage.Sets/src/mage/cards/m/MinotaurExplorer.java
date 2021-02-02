
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author cbt33
 */
public final class MinotaurExplorer extends CardImpl {

    public MinotaurExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Minotaur Explorer enters the battlefield, sacrifice it unless you discard a card at random.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new DiscardCardCost(true))));
    }

    private MinotaurExplorer(final MinotaurExplorer card) {
        super(card);
    }

    @Override
    public MinotaurExplorer copy() {
        return new MinotaurExplorer(this);
    }
}

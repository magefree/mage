
package mage.cards.b;

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
 * @author Quercitron
 */
public final class BalduvianHorde extends CardImpl {

    public BalduvianHorde(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.HUMAN, SubType.BARBARIAN);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Balduvian Horde enters the battlefield, sacrifice it unless you discard a card at random.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new DiscardCardCost(true))));
    }

    private BalduvianHorde(final BalduvianHorde card) {
        super(card);
    }

    @Override
    public BalduvianHorde copy() {
        return new BalduvianHorde(this);
    }
}

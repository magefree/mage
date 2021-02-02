
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class DeepcavernImp extends CardImpl {

    public DeepcavernImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.IMP);
        this.subtype.add(SubType.REBEL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Echo-Discard a card.
        this.addAbility(new EchoAbility(new DiscardCardCost()));
    }

    private DeepcavernImp(final DeepcavernImp card) {
        super(card);
    }

    @Override
    public DeepcavernImp copy() {
        return new DeepcavernImp(this);
    }
}

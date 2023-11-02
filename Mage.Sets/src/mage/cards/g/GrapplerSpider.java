

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GrapplerSpider extends CardImpl {

    public GrapplerSpider (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.SPIDER);
        this.color.setGreen(true);        
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(ReachAbility.getInstance());
    }

    private GrapplerSpider(final GrapplerSpider card) {
        super(card);
    }

    @Override
    public GrapplerSpider copy() {
        return new GrapplerSpider(this);
    }

}

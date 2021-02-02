

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
 * @author BetaSteward_at_googlemail.com
 */
public final class GiantSpider extends CardImpl {

    public GiantSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.addAbility(ReachAbility.getInstance());
    }

    private GiantSpider(final GiantSpider card) {
        super(card);
    }

    @Override
    public GiantSpider copy() {
        return new GiantSpider(this);
    }

}

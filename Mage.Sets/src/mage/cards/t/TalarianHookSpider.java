package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class TalarianHookSpider extends CardImpl {

    public TalarianHookSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        
        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private TalarianHookSpider(final TalarianHookSpider card) {
        super(card);
    }

    @Override
    public TalarianHookSpider copy() {
        return new TalarianHookSpider(this);
    }
}

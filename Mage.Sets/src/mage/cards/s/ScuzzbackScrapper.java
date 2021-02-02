
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.WitherAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class ScuzzbackScrapper extends CardImpl {

    public ScuzzbackScrapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R/G}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(WitherAbility.getInstance());
    }

    private ScuzzbackScrapper(final ScuzzbackScrapper card) {
        super(card);
    }

    @Override
    public ScuzzbackScrapper copy() {
        return new ScuzzbackScrapper(this);
    }
}

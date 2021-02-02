
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class BenthicBehemoth extends CardImpl {

    public BenthicBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}{U}");
        this.subtype.add(SubType.SERPENT);

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        this.addAbility(new IslandwalkAbility());
    }

    private BenthicBehemoth(final BenthicBehemoth card) {
        super(card);
    }

    @Override
    public BenthicBehemoth copy() {
        return new BenthicBehemoth(this);
    }
}

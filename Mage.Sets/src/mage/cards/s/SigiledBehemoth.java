

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SigiledBehemoth extends CardImpl {

    public SigiledBehemoth (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{W}");
        this.subtype.add(SubType.BEAST);


        this.power = new MageInt(5);
        this.toughness = new MageInt(4);
        this.addAbility(new ExaltedAbility());
    }

    private SigiledBehemoth(final SigiledBehemoth card) {
        super(card);
    }

    @Override
    public SigiledBehemoth copy() {
        return new SigiledBehemoth(this);
    }

}

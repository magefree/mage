
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class KalonianBehemoth extends CardImpl {

    public KalonianBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        this.addAbility(ShroudAbility.getInstance());
    }

    private KalonianBehemoth(final KalonianBehemoth card) {
        super(card);
    }

    @Override
    public KalonianBehemoth copy() {
        return new KalonianBehemoth(this);
    }
}


package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class BrambleweftBehemoth extends CardImpl {

    public BrambleweftBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample.
        this.addAbility(TrampleAbility.getInstance());
    }

    private BrambleweftBehemoth(final BrambleweftBehemoth card) {
        super(card);
    }

    @Override
    public BrambleweftBehemoth copy() {
        return new BrambleweftBehemoth(this);
    }
}

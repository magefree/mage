
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ScaledBehemoth extends CardImpl {

    public ScaledBehemoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        
        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

    }

    private ScaledBehemoth(final ScaledBehemoth card) {
        super(card);
    }

    @Override
    public ScaledBehemoth copy() {
        return new ScaledBehemoth(this);
    }
}

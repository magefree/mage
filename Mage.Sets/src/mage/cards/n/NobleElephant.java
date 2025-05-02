

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BandingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class NobleElephant extends CardImpl {

    public NobleElephant (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.ELEPHANT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Banding
        this.addAbility(BandingAbility.getInstance());
    }

    private NobleElephant(final NobleElephant card) {
        super(card);
    }

    @Override
    public NobleElephant copy() {
        return new NobleElephant(this);
    }

}

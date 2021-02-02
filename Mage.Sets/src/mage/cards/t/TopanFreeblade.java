
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RenownAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class TopanFreeblade extends CardImpl {

    public TopanFreeblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance <i>(Attacking doesn't cause this creature to tap.)
        this.addAbility(VigilanceAbility.getInstance());

        // Renown 1
        this.addAbility(new RenownAbility(1));
    }

    private TopanFreeblade(final TopanFreeblade card) {
        super(card);
    }

    @Override
    public TopanFreeblade copy() {
        return new TopanFreeblade(this);
    }
}

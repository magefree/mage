
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class EldraziDevastator extends CardImpl {

    public EldraziDevastator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(8);
        this.toughness = new MageInt(9);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private EldraziDevastator(final EldraziDevastator card) {
        super(card);
    }

    @Override
    public EldraziDevastator copy() {
        return new EldraziDevastator(this);
    }
}



package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class ArgothianSwine extends CardImpl {

    public ArgothianSwine (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.BOAR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(TrampleAbility.getInstance());
    }

    private ArgothianSwine(final ArgothianSwine card) {
        super(card);
    }

    @Override
    public ArgothianSwine copy() {
        return new ArgothianSwine(this);
    }
}
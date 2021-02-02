
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GravelgillAxeshark extends CardImpl {

    public GravelgillAxeshark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U/B}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Persist
        this.addAbility(new PersistAbility());
    }

    private GravelgillAxeshark(final GravelgillAxeshark card) {
        super(card);
    }

    @Override
    public GravelgillAxeshark copy() {
        return new GravelgillAxeshark(this);
    }
}

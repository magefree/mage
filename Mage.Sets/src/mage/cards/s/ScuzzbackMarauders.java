
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.PersistAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class ScuzzbackMarauders extends CardImpl {

    public ScuzzbackMarauders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R/G}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Persist
        this.addAbility(new PersistAbility());

    }

    private ScuzzbackMarauders(final ScuzzbackMarauders card) {
        super(card);
    }

    @Override
    public ScuzzbackMarauders copy() {
        return new ScuzzbackMarauders(this);
    }
}

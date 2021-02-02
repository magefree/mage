
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class PilgrimOfTheFires extends CardImpl {

    public PilgrimOfTheFires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private PilgrimOfTheFires(final PilgrimOfTheFires card) {
        super(card);
    }

    @Override
    public PilgrimOfTheFires copy() {
        return new PilgrimOfTheFires(this);
    }
}

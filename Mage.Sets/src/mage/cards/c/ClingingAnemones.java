
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class ClingingAnemones extends CardImpl {

    public ClingingAnemones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.JELLYFISH);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Evolve
        this.addAbility(new EvolveAbility());
    }

    private ClingingAnemones(final ClingingAnemones card) {
        super(card);
    }

    @Override
    public ClingingAnemones copy() {
        return new ClingingAnemones(this);
    }
}

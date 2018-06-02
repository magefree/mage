
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.EvolveAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class Shambleshark extends CardImpl {

    public Shambleshark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{U}");
        this.subtype.add(SubType.FISH);
        this.subtype.add(SubType.CRAB);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Evolve
        this.addAbility(new EvolveAbility());
    }

    public Shambleshark(final Shambleshark card) {
        super(card);
    }

    @Override
    public Shambleshark copy() {
        return new Shambleshark(this);
    }
}

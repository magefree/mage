
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.SkulkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class FarbogRevenant extends CardImpl {

    public FarbogRevenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Skulk
        this.addAbility(new SkulkAbility());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private FarbogRevenant(final FarbogRevenant card) {
        super(card);
    }

    @Override
    public FarbogRevenant copy() {
        return new FarbogRevenant(this);
    }
}

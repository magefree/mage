
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class GhirapurOsprey extends CardImpl {

    public GhirapurOsprey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private GhirapurOsprey(final GhirapurOsprey card) {
        super(card);
    }

    @Override
    public GhirapurOsprey copy() {
        return new GhirapurOsprey(this);
    }
}

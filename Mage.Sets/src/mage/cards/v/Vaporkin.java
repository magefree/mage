
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CanBlockOnlyFlyingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class Vaporkin extends CardImpl {

    public Vaporkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Vaporkin can block only creatures with flying.
         this.addAbility(new CanBlockOnlyFlyingAbility());
    }

    private Vaporkin(final Vaporkin card) {
        super(card);
    }

    @Override
    public Vaporkin copy() {
        return new Vaporkin(this);
    }
}

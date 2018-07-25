package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class VoidRay extends CardImpl {

    public VoidRay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Void Ray deals combat damage to a player, put a psi counter on it.
        // Void Ray gets +1/+1 for each psi counter on it.
    }

    public VoidRay(final VoidRay card) {
        super(card);
    }

    @Override
    public VoidRay copy() {
        return new VoidRay(this);
    }
}

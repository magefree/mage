
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class ArcboundStinger extends CardImpl {

    public ArcboundStinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Modular 1
        this.addAbility(new ModularAbility(this, 1));
    }

    private ArcboundStinger(final ArcboundStinger card) {
        super(card);
    }

    @Override
    public ArcboundStinger copy() {
        return new ArcboundStinger(this);
    }
}


package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class ArcboundHybrid extends CardImpl {

    public ArcboundHybrid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Modular 2
        this.addAbility(new ModularAbility(this, 2));
    }

    private ArcboundHybrid(final ArcboundHybrid card) {
        super(card);
    }

    @Override
    public ArcboundHybrid copy() {
        return new ArcboundHybrid(this);
    }
}

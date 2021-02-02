
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class ArcboundBruiser extends CardImpl {

    public ArcboundBruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Modular 3
        this.addAbility(new ModularAbility(this, 3));
    }

    private ArcboundBruiser(final ArcboundBruiser card) {
        super(card);
    }

    @Override
    public ArcboundBruiser copy() {
        return new ArcboundBruiser(this);
    }
}

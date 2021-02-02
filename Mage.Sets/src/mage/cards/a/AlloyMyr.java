
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North, Loki
 */
public final class AlloyMyr extends CardImpl {

    public AlloyMyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new AnyColorManaAbility());
    }

    private AlloyMyr(final AlloyMyr card) {
        super(card);
    }

    @Override
    public AlloyMyr copy() {
        return new AlloyMyr(this);
    }
}

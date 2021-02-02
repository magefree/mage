
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox

 */
public final class JungleLion extends CardImpl {

    public JungleLion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Jungle Lion can't block.
        this.addAbility(new CantBlockAbility());
    }

    private JungleLion(final JungleLion card) {
        super(card);
    }

    @Override
    public JungleLion copy() {
        return new JungleLion(this);
    }
}

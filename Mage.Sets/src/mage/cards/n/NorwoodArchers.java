
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class NorwoodArchers extends CardImpl {

    public NorwoodArchers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private NorwoodArchers(final NorwoodArchers card) {
        super(card);
    }

    @Override
    public NorwoodArchers copy() {
        return new NorwoodArchers(this);
    }
}

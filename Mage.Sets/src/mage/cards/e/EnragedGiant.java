
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ImproviseAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class EnragedGiant extends CardImpl {

    public EnragedGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Improvise <i>(Your artifacts can help cast this spell. Each artifact you tap after you're done activating mana abilities pays for {1}.)
        this.addAbility(new ImproviseAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // haste.
        this.addAbility(HasteAbility.getInstance());

    }

    private EnragedGiant(final EnragedGiant card) {
        super(card);
    }

    @Override
    public EnragedGiant copy() {
        return new EnragedGiant(this);
    }
}

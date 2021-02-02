
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class BastionInventor extends CardImpl {

    public BastionInventor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");
        
        this.subtype.add(SubType.VEDALKEN, SubType.ARTIFICER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Improvise
        this.addAbility(new ImproviseAbility());
        // Hexproof
        this.addAbility(HexproofAbility.getInstance());
    }

    private BastionInventor(final BastionInventor card) {
        super(card);
    }

    @Override
    public BastionInventor copy() {
        return new BastionInventor(this);
    }
}

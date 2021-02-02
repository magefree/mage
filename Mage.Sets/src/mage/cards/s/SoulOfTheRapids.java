
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class SoulOfTheRapids extends CardImpl {

    public SoulOfTheRapids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

    }

    private SoulOfTheRapids(final SoulOfTheRapids card) {
        super(card);
    }

    @Override
    public SoulOfTheRapids copy() {
        return new SoulOfTheRapids(this);
    }
}


package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class GoldenHind extends CardImpl {

    public GoldenHind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // T: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private GoldenHind(final GoldenHind card) {
        super(card);
    }

    @Override
    public GoldenHind copy() {
        return new GoldenHind(this);
    }
}

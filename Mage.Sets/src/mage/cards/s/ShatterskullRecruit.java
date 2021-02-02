
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ShatterskullRecruit extends CardImpl {

    public ShatterskullRecruit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private ShatterskullRecruit(final ShatterskullRecruit card) {
        super(card);
    }

    @Override
    public ShatterskullRecruit copy() {
        return new ShatterskullRecruit(this);
    }
}

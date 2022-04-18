
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class TreespringLorian extends CardImpl {

    public TreespringLorian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Morph {5}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{5}{G}")));
    }

    private TreespringLorian(final TreespringLorian card) {
        super(card);
    }

    @Override
    public TreespringLorian copy() {
        return new TreespringLorian(this);
    }
}

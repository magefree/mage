
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlankingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class JolraelsCentaur extends CardImpl {

    public JolraelsCentaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Shroud
        this.addAbility(ShroudAbility.getInstance());
        // Flanking
        this.addAbility(new FlankingAbility());
    }

    private JolraelsCentaur(final JolraelsCentaur card) {
        super(card);
    }

    @Override
    public JolraelsCentaur copy() {
        return new JolraelsCentaur(this);
    }
}

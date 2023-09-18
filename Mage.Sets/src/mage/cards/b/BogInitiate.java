
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class BogInitiate extends CardImpl {

    public BogInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}: Add {B}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlackMana(1), new ManaCostsImpl<>("{1}")));
    }

    private BogInitiate(final BogInitiate card) {
        super(card);
    }

    @Override
    public BogInitiate copy() {
        return new BogInitiate(this);
    }
}

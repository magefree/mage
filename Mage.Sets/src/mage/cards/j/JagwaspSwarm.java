

package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class JagwaspSwarm extends CardImpl {

    public JagwaspSwarm (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.INSECT);
        this.color.setBlack(true);        
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }

    private JagwaspSwarm(final JagwaspSwarm card) {
        super(card);
    }

    @Override
    public JagwaspSwarm copy() {
        return new JagwaspSwarm(this);
    }

}

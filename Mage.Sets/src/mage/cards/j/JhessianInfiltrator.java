

package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class JhessianInfiltrator extends CardImpl {

    public JhessianInfiltrator (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private JhessianInfiltrator(final JhessianInfiltrator card) {
        super(card);
    }

    @Override
    public JhessianInfiltrator copy() {
        return new JhessianInfiltrator(this);
    }

}

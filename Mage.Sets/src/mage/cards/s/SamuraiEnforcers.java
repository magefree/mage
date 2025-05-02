

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SamuraiEnforcers extends CardImpl {

    public SamuraiEnforcers (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new BushidoAbility(2));
    }

    private SamuraiEnforcers(final SamuraiEnforcers card) {
        super(card);
    }

    @Override
    public SamuraiEnforcers copy() {
        return new SamuraiEnforcers(this);
    }

}

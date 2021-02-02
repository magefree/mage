

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class EmeraldOryx extends CardImpl {

    public EmeraldOryx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");

        this.subtype.add(SubType.ANTELOPE);
        this.power = new MageInt(2);
    this.toughness = new MageInt(3);
        this.addAbility(new ForestwalkAbility());
    }

    private EmeraldOryx(final EmeraldOryx card) {
        super(card);
    }

    @Override
    public EmeraldOryx copy() {
        return new EmeraldOryx(this);
    }

}

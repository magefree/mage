

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GreaterBasilisk extends CardImpl {

    public GreaterBasilisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.BASILISK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        this.addAbility(DeathtouchAbility.getInstance());
    }

    private GreaterBasilisk(final GreaterBasilisk card) {
        super(card);
    }

    @Override
    public GreaterBasilisk copy() {
        return new GreaterBasilisk(this);
    }

}

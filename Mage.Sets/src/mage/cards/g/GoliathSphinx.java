

package mage.cards.g;

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
public final class GoliathSphinx extends CardImpl {

    public GoliathSphinx (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(8);
        this.toughness = new MageInt(7);
        this.addAbility(FlyingAbility.getInstance());
    }

    private GoliathSphinx(final GoliathSphinx card) {
        super(card);
    }

    @Override
    public GoliathSphinx copy() {
        return new GoliathSphinx(this);
    }

}

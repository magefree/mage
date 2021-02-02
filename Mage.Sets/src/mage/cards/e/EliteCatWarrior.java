
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
 * @author Plopman
 */
public final class EliteCatWarrior extends CardImpl {

    public EliteCatWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Forestwalk
        this.addAbility(new ForestwalkAbility());
    }

    private EliteCatWarrior(final EliteCatWarrior card) {
        super(card);
    }

    @Override
    public EliteCatWarrior copy() {
        return new EliteCatWarrior(this);
    }
}

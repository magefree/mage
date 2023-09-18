
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class MysticOfTheHiddenWay extends CardImpl {

    public MysticOfTheHiddenWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Mystic of the Hidden Way can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
        // Morph {2}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{U}")));
    }

    private MysticOfTheHiddenWay(final MysticOfTheHiddenWay card) {
        super(card);
    }

    @Override
    public MysticOfTheHiddenWay copy() {
        return new MysticOfTheHiddenWay(this);
    }
}

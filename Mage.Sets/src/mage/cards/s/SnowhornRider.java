
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SnowhornRider extends CardImpl {

    public SnowhornRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{U}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Morph {2}{G}{U}{R}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{G}{U}{R}")));
    }

    private SnowhornRider(final SnowhornRider card) {
        super(card);
    }

    @Override
    public SnowhornRider copy() {
        return new SnowhornRider(this);
    }
}

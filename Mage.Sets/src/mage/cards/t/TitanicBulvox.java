
package mage.cards.t;

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
 * @author LoneFox
 */
public final class TitanicBulvox extends CardImpl {

    public TitanicBulvox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Morph {4}{G}{G}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{G}{G}{G}")));
    }

    private TitanicBulvox(final TitanicBulvox card) {
        super(card);
    }

    @Override
    public TitanicBulvox copy() {
        return new TitanicBulvox(this);
    }
}

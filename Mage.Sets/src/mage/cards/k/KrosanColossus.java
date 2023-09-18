
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class KrosanColossus extends CardImpl {

    public KrosanColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Morph {6}{G}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{6}{G}{G}")));
    }

    private KrosanColossus(final KrosanColossus card) {
        super(card);
    }

    @Override
    public KrosanColossus copy() {
        return new KrosanColossus(this);
    }
}

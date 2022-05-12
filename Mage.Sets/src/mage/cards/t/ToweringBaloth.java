
package mage.cards.t;

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
 * @author LoneFox
 */
public final class ToweringBaloth extends CardImpl {

    public ToweringBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Morph {6}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{6}{G}")));
    }

    private ToweringBaloth(final ToweringBaloth card) {
        super(card);
    }

    @Override
    public ToweringBaloth copy() {
        return new ToweringBaloth(this);
    }
}


package mage.cards.s;

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
public final class ScornfulEgotist extends CardImpl {

    public ScornfulEgotist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{7}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Morph {U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{U}")));
    }

    private ScornfulEgotist(final ScornfulEgotist card) {
        super(card);
    }

    @Override
    public ScornfulEgotist copy() {
        return new ScornfulEgotist(this);
    }
}

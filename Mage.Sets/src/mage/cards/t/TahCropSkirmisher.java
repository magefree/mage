
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.EmbalmAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class TahCropSkirmisher extends CardImpl {

    public TahCropSkirmisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Embalm {3}{U}
        this.addAbility(new EmbalmAbility(new ManaCostsImpl<>("{3}{U}"), this));

    }

    private TahCropSkirmisher(final TahCropSkirmisher card) {
        super(card);
    }

    @Override
    public TahCropSkirmisher copy() {
        return new TahCropSkirmisher(this);
    }
}

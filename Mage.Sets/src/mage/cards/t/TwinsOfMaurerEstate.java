
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class TwinsOfMaurerEstate extends CardImpl {

    public TwinsOfMaurerEstate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Madness {2}{B} <i>(If you discard this card, discard it into exile. When you do, cast it for its madness cost or put it into your graveyard.)</i>
        this.addAbility(new MadnessAbility(new ManaCostsImpl<>("{2}{B}")));
    }

    private TwinsOfMaurerEstate(final TwinsOfMaurerEstate card) {
        super(card);
    }

    @Override
    public TwinsOfMaurerEstate copy() {
        return new TwinsOfMaurerEstate(this);
    }
}

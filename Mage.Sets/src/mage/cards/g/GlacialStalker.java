
package mage.cards.g;

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
 * @author LevelX2
 */
public final class GlacialStalker extends CardImpl {

    public GlacialStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Morph {4}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{4}{U}")));
    }

    private GlacialStalker(final GlacialStalker card) {
        super(card);
    }

    @Override
    public GlacialStalker copy() {
        return new GlacialStalker(this);
    }
}


package mage.cards.w;

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
public final class WitnessOfTheAges extends CardImpl {

    public WitnessOfTheAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{6}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Morph {5}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{5}")));
    }

    private WitnessOfTheAges(final WitnessOfTheAges card) {
        super(card);
    }

    @Override
    public WitnessOfTheAges copy() {
        return new WitnessOfTheAges(this);
    }
}

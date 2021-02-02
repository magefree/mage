
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class RipscalePredator extends CardImpl {

    public RipscalePredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.DINOSAUR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Ripscale Predator can't be blocked except by two or more creatures.
        this.addAbility(new MenaceAbility());
    }

    private RipscalePredator(final RipscalePredator card) {
        super(card);
    }

    @Override
    public RipscalePredator copy() {
        return new RipscalePredator(this);
    }
}

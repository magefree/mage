
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class SliptideSerpent extends CardImpl {

    public SliptideSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {3}{U}: Return Sliptide Serpent to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{3}{U}")));
    }

    private SliptideSerpent(final SliptideSerpent card) {
        super(card);
    }

    @Override
    public SliptideSerpent copy() {
        return new SliptideSerpent(this);
    }
}

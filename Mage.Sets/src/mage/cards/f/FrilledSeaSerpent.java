
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class FrilledSeaSerpent extends CardImpl {

    public FrilledSeaSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // {5}{U}{U}: Frilled Sea Serpent can't be blocked this turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new ManaCostsImpl<>("{5}{U}{U}")
        ));
    }

    private FrilledSeaSerpent(final FrilledSeaSerpent card) {
        super(card);
    }

    @Override
    public FrilledSeaSerpent copy() {
        return new FrilledSeaSerpent(this);
    }
}

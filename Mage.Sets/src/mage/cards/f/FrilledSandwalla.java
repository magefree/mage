
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author ciaccona007
 */
public final class FrilledSandwalla extends CardImpl {

    public FrilledSandwalla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{G}: Frilled Sandwalla gets +2/+2 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 2, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{G}")));
    }

    private FrilledSandwalla(final FrilledSandwalla card) {
        super(card);
    }

    @Override
    public FrilledSandwalla copy() {
        return new FrilledSandwalla(this);
    }
}

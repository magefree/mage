
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;

/**
 * @author nantuko
 */
public final class RallyThePeasants extends CardImpl {

    public RallyThePeasants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Creatures you control get +2/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 0, Duration.EndOfTurn));

        // Flashback {2}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{2}{R}")));
    }

    private RallyThePeasants(final RallyThePeasants card) {
        super(card);
    }

    @Override
    public RallyThePeasants copy() {
        return new RallyThePeasants(this);
    }
}

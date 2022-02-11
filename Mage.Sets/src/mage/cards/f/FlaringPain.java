
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;

/**
 *
 * @author fireshoes
 */
public final class FlaringPain extends CardImpl {

    public FlaringPain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Damage can't be prevented this turn.
        this.getSpellAbility().addEffect(new DamageCantBePreventedEffect(Duration.EndOfTurn, "Damage can't be prevented this turn", false, false));
        // Flashback {R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{R}")));
    }

    private FlaringPain(final FlaringPain card) {
        super(card);
    }

    @Override
    public FlaringPain copy() {
        return new FlaringPain(this);
    }
}

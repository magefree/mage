
package mage.cards.e;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageToTargetMultiAmountEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.target.common.TargetAnyTargetAmount;

/**
 *
 * @author TheElk801
 */
public final class Embolden extends CardImpl {

    public Embolden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Prevent the next 4 damage that would be dealt this turn to any number of target creatures and/or players, divided as you choose.
        this.getSpellAbility().addEffect(new PreventDamageToTargetMultiAmountEffect(Duration.EndOfTurn, 4));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(4));

        // Flashback {1}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{1}{W}")));

    }

    private Embolden(final Embolden card) {
        super(card);
    }

    @Override
    public Embolden copy() {
        return new Embolden(this);
    }
}

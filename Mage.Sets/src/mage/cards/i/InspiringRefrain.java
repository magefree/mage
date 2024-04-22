package mage.cards.i;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSpellWithTimeCountersEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InspiringRefrain extends CardImpl {

    public InspiringRefrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Draw two cards. Exile Inspiring Refrain with three time counters on it.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new ExileSpellWithTimeCountersEffect(3));

        // Suspend 3—{2}{U}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{2}{U}"), this));
    }

    private InspiringRefrain(final InspiringRefrain card) {
        super(card);
    }

    @Override
    public InspiringRefrain copy() {
        return new InspiringRefrain(this);
    }
}

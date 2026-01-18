
package mage.cards.l;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LiveFast extends CardImpl {

    public LiveFast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // You draw two cards, lose 2 life, and get {E}{E}.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2, true));
        this.getSpellAbility().addEffect(new LoseLifeSourceControllerEffect(2).setText(", lose 2 life"));
        this.getSpellAbility().addEffect(new GetEnergyCountersControllerEffect(2).setText(", and get {E}{E} <i>(two energy counters)</i>."));
    }

    private LiveFast(final LiveFast card) {
        super(card);
    }

    @Override
    public LiveFast copy() {
        return new LiveFast(this);
    }
}

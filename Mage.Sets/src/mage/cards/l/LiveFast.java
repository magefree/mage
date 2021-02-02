
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class LiveFast extends CardImpl {

    public LiveFast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");

        // You draw two cards, lose 2 life, and get {E}{E}.
        Effect effect = new DrawCardSourceControllerEffect(2);
        effect.setText("You draw two cards");
        getSpellAbility().addEffect(effect);
        effect = new LoseLifeSourceControllerEffect(2);
        effect.setText(", lose 2 life");
        getSpellAbility().addEffect(effect);
        effect = new GetEnergyCountersControllerEffect(2);
        effect.setText(", and get {E}{E} <i>(two energy counters)</i>.");
        getSpellAbility().addEffect(effect);
    }

    private LiveFast(final LiveFast card) {
        super(card);
    }

    @Override
    public LiveFast copy() {
        return new LiveFast(this);
    }
}

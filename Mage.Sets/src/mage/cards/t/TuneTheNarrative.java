package mage.cards.t;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TuneTheNarrative extends CardImpl {

    public TuneTheNarrative(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");

        // Draw a card. You get {E}{E}.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addEffect(new GetEnergyCountersControllerEffect(2));
    }

    private TuneTheNarrative(final TuneTheNarrative card) {
        super(card);
    }

    @Override
    public TuneTheNarrative copy() {
        return new TuneTheNarrative(this);
    }
}

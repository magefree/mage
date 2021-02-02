package mage.cards.t;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author North
 */
public final class TezzeretsGambit extends CardImpl {

    public TezzeretsGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U/P}");

        // Draw two cards, then proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new ProliferateEffect().concatBy(", then"));
    }

    private TezzeretsGambit(final TezzeretsGambit card) {
        super(card);
    }

    @Override
    public TezzeretsGambit copy() {
        return new TezzeretsGambit(this);
    }
}

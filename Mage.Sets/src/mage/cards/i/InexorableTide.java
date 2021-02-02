package mage.cards.i;

import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Loki, North
 */
public final class InexorableTide extends CardImpl {

    public InexorableTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        // Whenever you cast a spell, proliferate. (You choose any number of permanents and/or players with counters on them, then give each another counter of a kind already there.)
        this.addAbility(new SpellCastControllerTriggeredAbility(new ProliferateEffect(), false));
    }

    private InexorableTide(final InexorableTide card) {
        super(card);
    }

    @Override
    public InexorableTide copy() {
        return new InexorableTide(this);
    }

}

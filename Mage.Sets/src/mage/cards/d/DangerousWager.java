package mage.cards.d;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author North
 */
public final class DangerousWager extends CardImpl {

    public DangerousWager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Discard your hand, then draw two cards.
        this.getSpellAbility().addEffect(new DiscardHandControllerEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2).setText(", then draw two cards"));
    }

    private DangerousWager(final DangerousWager card) {
        super(card);
    }

    @Override
    public DangerousWager copy() {
        return new DangerousWager(this);
    }
}

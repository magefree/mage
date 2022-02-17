package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author TheElk801
 */
public final class HellSwarm extends CardImpl {

    public HellSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // All creatures get -1/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-1, 0, Duration.EndOfTurn));
    }

    private HellSwarm(final HellSwarm card) {
        super(card);
    }

    @Override
    public HellSwarm copy() {
        return new HellSwarm(this);
    }
}

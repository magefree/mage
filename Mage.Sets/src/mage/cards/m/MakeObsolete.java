package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 * @author royk
 */
public final class MakeObsolete extends CardImpl {

    public MakeObsolete(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}");

        // Creatures your opponents control get -1/-1  until end of turn
        this.getSpellAbility().addEffect(new BoostOpponentsEffect(-1, -1, Duration.EndOfTurn));
    }

    private MakeObsolete(final MakeObsolete card) {
        super(card);
    }

    @Override
    public MakeObsolete copy() {
        return new MakeObsolete(this);
    }
}

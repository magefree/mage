package mage.cards.c;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CosmotronicWave extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterOpponentsCreaturePermanent("creature your opponents control");

    public CosmotronicWave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Cosmotronic Wave deals 1 damage to each creature your opponents control. Creatures your opponents control can't block this turn.
        this.getSpellAbility().addEffect(new DamageAllEffect(1, filter));
        this.getSpellAbility().addEffect(new CantBlockAllEffect(filter, Duration.EndOfTurn).setText("Creatures your opponents control can't block this turn"));
    }

    private CosmotronicWave(final CosmotronicWave card) {
        super(card);
    }

    @Override
    public CosmotronicWave copy() {
        return new CosmotronicWave(this);
    }
}

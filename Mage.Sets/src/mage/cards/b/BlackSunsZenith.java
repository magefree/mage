

package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public final class BlackSunsZenith extends CardImpl {

    public BlackSunsZenith (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{B}{B}");

        // Put X -1/-1 counters on each creature. Shuffle Black Sun's Zenith into its owner's library.
        this.getSpellAbility().addEffect(new BlackSunsZenithEffect());
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
    }

    private BlackSunsZenith(final BlackSunsZenith card) {
        super(card);
    }

    @Override
    public BlackSunsZenith copy() {
        return new BlackSunsZenith(this);
    }

}

class BlackSunsZenithEffect extends OneShotEffect {
    BlackSunsZenithEffect() {
        super(Outcome.UnboostCreature);
        staticText = "Put X -1/-1 counters on each creature";
    }

    private BlackSunsZenithEffect(final BlackSunsZenithEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = source.getManaCostsToPay().getX();
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            if (permanent != null && permanent.isCreature(game)) {
                permanent.addCounters(CounterType.M1M1.createInstance(amount), source.getControllerId(), source, game);
            }
        }
        return true;
    }

    @Override
    public BlackSunsZenithEffect copy() {
        return new BlackSunsZenithEffect(this);
    }

}
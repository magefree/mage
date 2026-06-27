package mage.cards.a;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AlienHasteToken;

/**
 *
 * @author muz
 */
public final class AlienInvasion extends CardImpl {

    public AlienInvasion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}{G}");

        // At the beginning of combat on your turn, create a 1/1 red Alien creature token with haste and "This token attacks each combat if able." Put a +1/+1 counter on it for each invasion counter on this enchantment, then put an invasion counter on this enchantment.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AlienInvasionEffect()));
    }

    private AlienInvasion(final AlienInvasion card) {
        super(card);
    }

    @Override
    public AlienInvasion copy() {
        return new AlienInvasion(this);
    }
}

class AlienInvasionEffect extends OneShotEffect {

    AlienInvasionEffect() {
        super(Outcome.Benefit);
        staticText = "create a 1/1 red Alien creature token with haste and \"This token attacks each combat if able.\" "
                + "Put a +1/+1 counter on it for each invasion counter on this enchantment, "
                + "then put an invasion counter on this enchantment";
    }

    private AlienInvasionEffect(final AlienInvasionEffect effect) {
        super(effect);
    }

    @Override
    public AlienInvasionEffect copy() {
        return new AlienInvasionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null) {
            return false;
        }
        int invasionCounters = sourcePermanent.getCounters(game).getCount(CounterType.INVASION);
        CreateTokenEffect tokenEffect = new CreateTokenEffect(new AlienHasteToken());
        if (!tokenEffect.apply(game, source)) {
            return false;
        }
        if (invasionCounters > 0) {
            tokenEffect.getLastAddedTokenIds()
                    .stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .forEach(token -> token.addCounters(CounterType.P1P1.createInstance(invasionCounters), source, game));
        }
        sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent != null) {
            sourcePermanent.addCounters(CounterType.INVASION.createInstance(), source, game);
        }
        return true;
    }
}

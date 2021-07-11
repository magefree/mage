package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeferisTimeTwist extends CardImpl {

    public TeferisTimeTwist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Exile target permanent you control. Return that card to the battlefield under its owner's control at the beginning of the next end step. If it enters the battlefield as a creature, it enters with an additional +1/+1 counter on it.
        this.getSpellAbility().addEffect(new TeferisTimeTwistEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
    }

    private TeferisTimeTwist(final TeferisTimeTwist card) {
        super(card);
    }

    @Override
    public TeferisTimeTwist copy() {
        return new TeferisTimeTwist(this);
    }
}

class TeferisTimeTwistEffect extends OneShotEffect {

    TeferisTimeTwistEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target permanent you control. Return that card to the battlefield " +
                "under its owner's control at the beginning of the next end step. " +
                "If it enters the battlefield as a creature, it enters with an additional +1/+1 counter on it.";
    }

    private TeferisTimeTwistEffect(final TeferisTimeTwistEffect effect) {
        super(effect);
    }

    @Override
    public TeferisTimeTwistEffect copy() {
        return new TeferisTimeTwistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        Effect effect = new TeferisTimeTwistReturnEffect(new MageObjectReference(
                permanent.getId(), permanent.getZoneChangeCounter(game) + 1, game
        ));
        if (!player.moveCards(permanent, Zone.EXILED, source, game)) {
            return false;
        }
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        return true;
    }
}

class TeferisTimeTwistReturnEffect extends OneShotEffect {

    private final MageObjectReference mor;

    TeferisTimeTwistReturnEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        staticText = "return the exiled card to the battlefield";
        this.mor = mor;
    }

    private TeferisTimeTwistReturnEffect(final TeferisTimeTwistReturnEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public TeferisTimeTwistReturnEffect copy() {
        return new TeferisTimeTwistReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = mor.getCard(game);
        if (card == null) {
            return false;
        }
        Player player = game.getPlayer(card.getOwnerId());
        if (player == null) {
            return false;
        }
        if (!player.moveCards(card, Zone.BATTLEFIELD, source, game)) {
            return true;
        }
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null && permanent.isCreature(game)) {
            // TODO: This is technically wrong as it should enter with the counters,
            // however there's currently no way to know that for sure
            // this is similar to the blood moon issue
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        }
        return true;
    }
}
package mage.cards.s;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainSuspendEffect;
import mage.abilities.keyword.SuspendAbility;
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
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Suspend extends CardImpl {

    public Suspend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Exile target creature and put two time counters on it. If it doesn't have suspend, it gains suspend.
        this.getSpellAbility().addEffect(new SuspendEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Suspend(final Suspend card) {
        super(card);
    }

    @Override
    public Suspend copy() {
        return new Suspend(this);
    }
}

class SuspendEffect extends OneShotEffect {

    SuspendEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature and put two time counters on it. " +
                "If it doesn't have suspend, it gains suspend";
    }

    private SuspendEffect(final SuspendEffect effect) {
        super(effect);
    }

    @Override
    public SuspendEffect copy() {
        return new SuspendEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || permanent == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        if (!controller.moveCards(permanent, Zone.EXILED, source, game)
                || game.getState().getZone(card.getId()) != Zone.EXILED) {
            return true;
        }
        UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
        if (!controller.moveCardToExileWithInfo(
                card, exileId, "Suspended cards of " + controller.getName(),
                source, game, Zone.HAND, true
        )) {
            return true;
        }
        card.addCounters(CounterType.TIME.createInstance(2), source.getControllerId(), source, game);
        if (!card.getAbilities(game).containsClass(SuspendAbility.class)) {
            game.addEffect(new GainSuspendEffect(new MageObjectReference(card, game)), source);
        }
        game.informPlayers(controller.getLogName() + " suspends 2 - " + card.getName());
        return true;
    }
}

package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VorinclexMonstrousRaider extends CardImpl {

    public VorinclexMonstrousRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // If you would put one or more counters on a permanent or player, put twice that many of each of those kinds of counters on that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(new VorinclexMonstrousRaiderEffect()));

        // If an opponent would put one or more counters on a permanent or player, they put half that many of each of those kinds of counters on that permanent or player instead, rounded down.
        this.addAbility(new SimpleStaticAbility(new InfoEffect(
                "if an opponent would put one or more counters on a permanent or player, " +
                        "they put half that many of each of those kinds of counters " +
                        "on that permanent or player instead, rounded down"
        )));
    }

    private VorinclexMonstrousRaider(final VorinclexMonstrousRaider card) {
        super(card);
    }

    @Override
    public VorinclexMonstrousRaider copy() {
        return new VorinclexMonstrousRaider(this);
    }
}

class VorinclexMonstrousRaiderEffect extends ReplacementEffectImpl {

    VorinclexMonstrousRaiderEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "if you would put one or more counters on a permanent or player, " +
                "put twice that many of each of those kinds of counters on that permanent or player instead";
    }

    private VorinclexMonstrousRaiderEffect(final VorinclexMonstrousRaiderEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if (source.isControlledBy(event.getPlayerId())) {
            event.setAmountForCounters(CardUtil.overflowMultiply(event.getAmount(), 2), true);
        } else if (game.getOpponents(event.getPlayerId()).contains(source.getControllerId())) {
            event.setAmountForCounters(Math.floorDiv(event.getAmount(), 2), true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player targetPlayer = game.getPlayer(event.getTargetId());
        Permanent targetPermanent = game.getPermanentEntering(event.getTargetId());
        if (targetPermanent == null) {
            targetPermanent = game.getPermanent(event.getTargetId());
        }

        // on a permanent or player
        if (targetPlayer == null && targetPermanent == null) {
            return false;
        }

        return source.isControlledBy(event.getPlayerId())
                || game.getOpponents(event.getPlayerId()).contains(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public VorinclexMonstrousRaiderEffect copy() {
        return new VorinclexMonstrousRaiderEffect(this);
    }
}

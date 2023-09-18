package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class MaliciousMalfunction extends CardImpl {

    public MaliciousMalfunction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // All creatures get -2/-2 until end of turn. If a creature would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new MaliciousMalfunctionReplacementEffect());
    }

    private MaliciousMalfunction(final MaliciousMalfunction card) {
        super(card);
    }

    @Override
    public MaliciousMalfunction copy() {
        return new MaliciousMalfunction(this);
    }
}

class MaliciousMalfunctionReplacementEffect extends ReplacementEffectImpl {

    public MaliciousMalfunctionReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.staticText = "If a creature would die this turn, exile it instead";
    }

    private MaliciousMalfunctionReplacementEffect(final MaliciousMalfunctionReplacementEffect effect) {
        super(effect);
    }

    @Override
    public MaliciousMalfunctionReplacementEffect copy() {
        return new MaliciousMalfunctionReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent) event).getTarget();
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                return player.moveCards(permanent, Zone.EXILED, source, game);
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getTarget() != null
                && zEvent.getTarget().isCreature(game)
                && zEvent.isDiesEvent();
    }
}

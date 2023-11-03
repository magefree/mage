package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MaliciousEclipse extends CardImpl {

    public MaliciousEclipse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // All creatures get -2/-2 until end of turn. If a creature an opponent controls would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new MaliciousEclipseReplacementEffect());
    }

    private MaliciousEclipse(final MaliciousEclipse card) {
        super(card);
    }

    @Override
    public MaliciousEclipse copy() {
        return new MaliciousEclipse(this);
    }
}

/**
 * Inspired by {@link MaliciousMalfunction}
 */
class MaliciousEclipseReplacementEffect extends ReplacementEffectImpl {

    public MaliciousEclipseReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.staticText = "If a creature an opponent controls would die this turn, exile it instead";
    }

    private MaliciousEclipseReplacementEffect(final MaliciousEclipseReplacementEffect effect) {
        super(effect);
    }

    @Override
    public MaliciousEclipseReplacementEffect copy() {
        return new MaliciousEclipseReplacementEffect(this);
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
        return zEvent.getTarget() != null &&
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE.match(
                        zEvent.getTarget(), source.getControllerId(),
                        source, game)
                && zEvent.isDiesEvent();
    }
}

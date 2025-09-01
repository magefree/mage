package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoadingZone extends CardImpl {

    public LoadingZone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // If one or more counters would be put on a creature, Spacecraft, or Planet you control, twice that many of each of those kinds of counters are put on it instead.
        this.addAbility(new SimpleStaticAbility(new LoadingZoneEffect()));

        // Warp {G}
        this.addAbility(new WarpAbility(this, "{G}"));
    }

    private LoadingZone(final LoadingZone card) {
        super(card);
    }

    @Override
    public LoadingZone copy() {
        return new LoadingZone(this);
    }
}

class LoadingZoneEffect extends ReplacementEffectImpl {

    LoadingZoneEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "if one or more counters would be put on a creature, Spacecraft, or Planet you control, " +
                "twice that many of each of those kinds of counters are put on it instead";
    }

    private LoadingZoneEffect(final LoadingZoneEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(CardUtil.overflowMultiply(event.getAmount(), 2), true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getAmount() < 1) {
            return false;
        }
        Permanent permanent = Optional
                .ofNullable(event)
                .map(GameEvent::getTargetId)
                .map(game::getPermanent)
                .orElse(game.getPermanentEntering(event.getTargetId()));
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && (permanent.isCreature(game)
                || permanent.hasSubtype(SubType.SPACECRAFT, game)
                || permanent.hasSubtype(SubType.PLANET, game));
    }

    @Override
    public LoadingZoneEffect copy() {
        return new LoadingZoneEffect(this);
    }
}

package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class WindingConstrictor extends CardImpl {

    public WindingConstrictor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{G}");

        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // If one or more counters would be put on an artifact or creature you control, that many plus one of each of those kinds of counters are put on that permanent instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WindingConstrictorPermanentEffect()));

        // If you would get one or more counters, you get that many plus one of each of those kinds of counters instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WindingConstrictorPlayerEffect()));
    }

    private WindingConstrictor(final WindingConstrictor card) {
        super(card);
    }

    @Override
    public WindingConstrictor copy() {
        return new WindingConstrictor(this);
    }
}

class WindingConstrictorPermanentEffect extends ReplacementEffectImpl {

    WindingConstrictorPermanentEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "If one or more counters would be put on an artifact or creature you control, "
                + "that many plus one of each of those kinds of counters are put on that permanent instead";
    }

    WindingConstrictorPermanentEffect(final WindingConstrictorPermanentEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(event.getAmount() + 1, true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null
                && event.getAmount() > 0
                && (permanent.isCreature(game) || permanent.isArtifact(game))
                && permanent.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public WindingConstrictorPermanentEffect copy() {
        return new WindingConstrictorPermanentEffect(this);
    }
}

class WindingConstrictorPlayerEffect extends ReplacementEffectImpl {

    WindingConstrictorPlayerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "If you would get one or more counters, you get that many plus one of each of those kinds of counters instead";
    }

    WindingConstrictorPlayerEffect(final WindingConstrictorPlayerEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(event.getAmount() + 1, true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getTargetId());
        return player != null && player.getId().equals(source.getControllerId()) && event.getAmount() > 0;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public WindingConstrictorPlayerEffect copy() {
        return new WindingConstrictorPlayerEffect(this);
    }
}

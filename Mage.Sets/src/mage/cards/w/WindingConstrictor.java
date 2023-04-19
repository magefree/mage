package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.replacement.ModifyCountersAddedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
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
        this.addAbility(new SimpleStaticAbility(new ModifyCountersAddedEffect(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE, null
        )));

        // If you would get one or more counters, you get that many plus one of each of those kinds of counters instead.
        this.addAbility(new SimpleStaticAbility(new WindingConstrictorPlayerEffect()));
    }

    private WindingConstrictor(final WindingConstrictor card) {
        super(card);
    }

    @Override
    public WindingConstrictor copy() {
        return new WindingConstrictor(this);
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

package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Stravant
 */
public final class VizierOfRemedies extends CardImpl {

    public VizierOfRemedies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // If one or more -1/-1 counters would be put on a creature you control, that many -1/-1 counters minus one are put on it instead.
        addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VizierOfRemediesReplacementEffect()));

    }

    private VizierOfRemedies(final VizierOfRemedies card) {
        super(card);
    }

    @Override
    public VizierOfRemedies copy() {
        return new VizierOfRemedies(this);
    }
}

class VizierOfRemediesReplacementEffect extends ReplacementEffectImpl {

    public VizierOfRemediesReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If one or more -1/-1 counters would be put on a creature you control, that many -1/-1 counters minus one are put on it instead";
    }

    public VizierOfRemediesReplacementEffect(final VizierOfRemediesReplacementEffect effect) {
        super(effect);
    }

    @Override
    public VizierOfRemediesReplacementEffect copy() {
        return new VizierOfRemediesReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(event.getAmount() - 1, true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getAmount() <= 0 || !event.getData().equals(CounterType.M1M1.getName())) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null && permanent.isControlledBy(source.getControllerId())
                && permanent.isCreature(game);
    }
}

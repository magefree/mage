package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConclaveMentor extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount(false);

    public ConclaveMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // If one or more +1/+1 counters would be put on a creature you control, that many plus one +1/+1 counters are put on that creature instead.
        this.addAbility(new SimpleStaticAbility(new ConclaveMentorEffect()));

        // When Conclave Mentor dies, you gain life equal to its power.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(xValue, "you gain life equal to its power")));
    }

    private ConclaveMentor(final ConclaveMentor card) {
        super(card);
    }

    @Override
    public ConclaveMentor copy() {
        return new ConclaveMentor(this);
    }
}

class ConclaveMentorEffect extends ReplacementEffectImpl {

    ConclaveMentorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "If one or more +1/+1 counters would be put on a creature you control, " +
                "that many plus one +1/+1 counters are put on that creature instead";
    }

    private ConclaveMentorEffect(final ConclaveMentorEffect effect) {
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
        if (event.getData().equals(CounterType.P1P1.getName()) && event.getAmount() > 0) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            return permanent != null && permanent.isControlledBy(source.getControllerId())
                    && permanent.isCreature(game);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ConclaveMentorEffect copy() {
        return new ConclaveMentorEffect(this);
    }
}

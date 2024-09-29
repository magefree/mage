package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.HaventCastSpellFromHandThisTurnCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class PrairieDog extends CardImpl {

    public PrairieDog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SQUIRREL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, if you haven't cast a spell from your hand this turn, put a +1/+1 counter on Prairie Dog.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                TargetController.YOU, HaventCastSpellFromHandThisTurnCondition.instance, false
        ).addHint(HaventCastSpellFromHandThisTurnCondition.hint));

        // {4}{W}: Until end of turn, if you would put one or more +1/+1 counters on a creature you control, put that many plus one +1/+1 counters on it instead.
        this.addAbility(new SimpleActivatedAbility(new PrairieDogReplacementEffect(), new ManaCostsImpl<>("{4}{W}")));
    }

    private PrairieDog(final PrairieDog card) {
        super(card);
    }

    @Override
    public PrairieDog copy() {
        return new PrairieDog(this);
    }
}

class PrairieDogReplacementEffect extends ReplacementEffectImpl {

    PrairieDogReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.BoostCreature, false);
        staticText = "Until end of turn, "
                + "if you would put one or more +1/+1 counters on a creature you control, "
                + "put that many plus one +1/+1 counters on it instead";
    }

    private PrairieDogReplacementEffect(final PrairieDogReplacementEffect effect) {
        super(effect);
    }

    @Override
    public PrairieDogReplacementEffect copy() {
        return new PrairieDogReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmountForCounters(CardUtil.overflowInc(event.getAmount(), 1), true);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ADD_COUNTERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return event.getAmount() > 0
                && source.isControlledBy(event.getPlayerId())
                && permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(source.getControllerId())
                && event.getData().equals(CounterType.P1P1.getName());
    }
}

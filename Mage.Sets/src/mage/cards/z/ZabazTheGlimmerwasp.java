package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZabazTheGlimmerwasp extends CardImpl {

    public ZabazTheGlimmerwasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Modular 1
        this.addAbility(new ModularAbility(this, 1));

        // If a modular triggered ability would put one or more +1/+1 counters on a creature you control, that many plus one +1/+1 counters are put on it instead.
        this.addAbility(new SimpleStaticAbility(new ZabazTheGlimmerwaspEffect()));

        // {R}: Destroy target artifact you control.
        Ability ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ManaCostsImpl<>("{R}"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        this.addAbility(ability);

        // {W}: Zabaz, the Glimmerwasp gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{W}")));
    }

    private ZabazTheGlimmerwasp(final ZabazTheGlimmerwasp card) {
        super(card);
    }

    @Override
    public ZabazTheGlimmerwasp copy() {
        return new ZabazTheGlimmerwasp(this);
    }
}

class ZabazTheGlimmerwaspEffect extends ReplacementEffectImpl {

    ZabazTheGlimmerwaspEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature, false);
        staticText = "if a modular triggered ability would put one or more +1/+1 counters on a creature you control, " +
                "that many plus one +1/+1 counters are put on it instead";
    }

    ZabazTheGlimmerwaspEffect(final ZabazTheGlimmerwaspEffect effect) {
        super(effect);
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
        if (!event.getData().equals(CounterType.P1P1.getName()) || event.getAmount() < 1) {
            return false;
        }
        StackObject stackAbility = game.getStack().getStackObject(event.getSourceId());
        if (stackAbility == null || !(stackAbility.getStackAbility() instanceof ModularAbility)) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(event.getTargetId());
        }
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && permanent.isCreature(game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ZabazTheGlimmerwaspEffect copy() {
        return new ZabazTheGlimmerwaspEffect(this);
    }
}

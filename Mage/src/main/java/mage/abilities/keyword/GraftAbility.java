
package mage.abilities.keyword;

import java.util.Locale;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 * 702.56. Graft 702.56a. Graft represents both a static ability and a triggered
 * ability. Graft N means, "This permanent enters the battlefield with N +1/+1
 * counters on it" and, "Whenever another creature enters the battlefield, if
 * this permanent has a +1/+1 counter on it, you may move a +1/+1 counter from
 * this permanent onto that creature."
 *
 * 702.56b. If a creature has multiple instances of graft, each one works
 * separately.
 *
 * @author LevelX2
 */
public class GraftAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    private int amount;
    private String cardtype;

    public GraftAbility(Card card, int amount) {
        super(Zone.BATTLEFIELD, new GraftDistributeCounterEffect(), true);
        this.amount = amount;
        StringBuilder sb = new StringBuilder();
        for (CardType theCardtype : card.getCardType()) {
            sb.append(theCardtype.toString().toLowerCase(Locale.ENGLISH)).append(' ');
        }
        this.cardtype = sb.toString().trim();
        addSubAbility(new GraftStaticAbility(amount));
    }

    public GraftAbility(final GraftAbility ability) {
        super(ability);
        this.amount = ability.amount;
        this.cardtype = ability.cardtype;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(this.getSourceId());
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (sourcePermanent != null
                && permanent != null
                && !sourcePermanent.getId().equals(permanent.getId())
                && sourcePermanent.getCounters(game).containsKey(CounterType.P1P1)
                && filter.match(permanent, game)) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public GraftAbility copy() {
        return new GraftAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Graft");
        sb.append(' ').append(amount).append(" <i>(This ").append(cardtype).append(" enters the battlefield with ")
                .append(amount == 1 ? "a" : CardUtil.numberToText(amount))
                .append(" +1/+1 counter on it. Whenever a creature enters the battlefield, you may move a +1/+1 counter from this ")
                .append(cardtype).append(" onto it.)</i>");
        return sb.toString();
    }

}

class GraftStaticAbility extends StaticAbility {

    private String ruleText;

    public GraftStaticAbility(int amount) {
        super(Zone.ALL, new EntersBattlefieldEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(amount))));
        ruleText = new StringBuilder("This enters the battlefield with ").append(amount).append(" +1/+1 counter on it.").toString();
        this.setRuleVisible(false);
    }

    public GraftStaticAbility(final GraftStaticAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public GraftStaticAbility copy() {
        return new GraftStaticAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}

class GraftDistributeCounterEffect extends OneShotEffect {

    public GraftDistributeCounterEffect() {
        super(Outcome.Detriment); // because you can move ot also to opponents creature
        this.staticText = "you may move a +1/+1 counter from this permanent onto it";
    }

    public GraftDistributeCounterEffect(final GraftDistributeCounterEffect effect) {
        super(effect);
    }

    @Override
    public GraftDistributeCounterEffect copy() {
        return new GraftDistributeCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            int numberOfCounters = sourcePermanent.getCounters(game).getCount(CounterType.P1P1);
            if (numberOfCounters > 0) {
                Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
                if (targetCreature != null) {
                    sourcePermanent.removeCounters(CounterType.P1P1.getName(), 1, source, game);
                    targetCreature.addCounters(CounterType.P1P1.createInstance(1), source.getControllerId(), source, game);
                    if (!game.isSimulation()) {
                        game.informPlayers("Moved one +1/+1 counter from " + sourcePermanent.getLogName() + " to " + targetCreature.getLogName());
                    }
                    return true;
                }
            }
        }
        return false;
    }
}

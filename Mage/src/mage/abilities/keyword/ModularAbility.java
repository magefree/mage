package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;
import mage.util.CardUtil;


/**
 *
 * 702.41. Modular
 *
 *   702.41a Modular represents both a static ability and a triggered ability.
 *           "Modular N" means "This permanent enters the battlefield with N +1/+1
 *           counters on it" and "When this permanent is put into a graveyard
 *           from the battlefield, you may put a +1/+1 counter on target artifact
 *           creature for each +1/+1 counter on this permanent."
 *   702.41b If a creature has multiple instances of modular, each one works separately.
 *
 * 
 * @author Loki, LevelX2
 */

public class ModularAbility extends DiesTriggeredAbility {
    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact creature");
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }
    private int amount;
    private boolean sunburst;

    public ModularAbility(Card card, int amount) {
        this(card, amount, false);
    }

    public ModularAbility(Card card, int amount, boolean sunburst) {
        super(new ModularDistributeCounterEffect(), true);
        Target target = new TargetArtifactPermanent(filter);
        target.setRequired(true);
        this.addTarget(target);
        this.amount = amount;
        this.sunburst = sunburst;
        if (sunburst) {
            Ability ability = new SunburstAbility(card);
            ability.setRuleVisible(false);
            card.addAbility(ability);
        } else {
            card.addAbility(new ModularStaticAbility(amount));
        }
    }

    public ModularAbility(ModularAbility ability) {
        super(ability);
        this.amount = ability.amount;
        this.sunburst = ability.sunburst;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
            if (zEvent.getTarget().getCounters().getCount(CounterType.P1P1) > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ModularAbility copy() {
        return new ModularAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Modular");
        if (sunburst) {
            sb.append("-Sunburst <i>(This enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it. When it dies, you may put its +1/+1 counters on target artifact creature.)</i>");
        } else {
            sb.append(" ").append(amount).append(" <i>(This enters the battlefield with ")
                          .append(CardUtil.numberToText(amount, "a"))
                .append(" +1/+1 counter").append(amount != 1 ? "s":"")
                .append(" on it. When it dies, you may put its +1/+1 counters on target artifact creature.)</i>");
        }
        return sb.toString();
    }

}

class ModularStaticAbility extends StaticAbility<ModularStaticAbility> {

    private String ruleText;

    public ModularStaticAbility(int amount) {
        super(Zone.BATTLEFIELD, new EntersBattlefieldEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(amount))));
        ruleText = new StringBuilder("This enters the battlefield with ").append(CardUtil.numberToText(amount, "a"))
                .append(" +1/+1 counter").append(amount != 1 ? "s":"")
                .append(" on it.").toString();
        this.setRuleVisible(false);
    }

    public ModularStaticAbility(final ModularStaticAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public ModularStaticAbility copy() {
        return new ModularStaticAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}


class ModularDistributeCounterEffect extends OneShotEffect<ModularDistributeCounterEffect> {
    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifact creature");
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public ModularDistributeCounterEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "you may put a +1/+1 counter on target artifact creature for each +1/+1 counter on this permanent";
    }

    public ModularDistributeCounterEffect(final ModularDistributeCounterEffect effect) {
        super(effect);
    }

    @Override
    public ModularDistributeCounterEffect copy() {
        return new ModularDistributeCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        Permanent targetArtifact = game.getPermanent(targetPointer.getFirst(game, source));
        Player player = game.getPlayer(source.getControllerId());
        if (sourcePermanent != null && targetArtifact != null && player != null) {
            int numberOfCounters = sourcePermanent.getCounters().getCount(CounterType.P1P1);
            if (numberOfCounters > 0) {
                targetArtifact.addCounters(CounterType.P1P1.createInstance(numberOfCounters), game);
            }
            return true;
        }
        return false;
    }
}

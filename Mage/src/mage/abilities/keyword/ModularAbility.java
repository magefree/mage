package mage.abilities.keyword;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.counters.CounterType;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;


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

    public ModularAbility(Card card, int amount) {
        super(new ModularDistributeCounterEffect(), true);
        this.addTarget(new TargetArtifactPermanent(filter));
        this.amount = amount;
        card.addAbility(new ModularStaticAbility(amount));
    }

    public ModularAbility(ModularAbility ability) {
        super(ability);
        this.amount = ability.amount;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        MageObject before = game.getLastKnownInformation(sourceId, Constants.Zone.BATTLEFIELD);
        if (before != null && ((Permanent) before).getCounters().getCount(CounterType.P1P1) > 0) {
            return super.checkTrigger(event, game);
        }
        return false;
    }

    @Override
    public ModularAbility copy() {
        return new ModularAbility(this);
    }

    @Override
    public String getRule() {
        return "Modular " + amount + " <i>(This enters the battlefield with " + amount + " +1/+1 counter on it. When it dies, you may put its +1/+1 counters on target artifact creature.)</i>";
    }

}

class ModularStaticAbility extends StaticAbility<ModularStaticAbility> {

    public ModularStaticAbility(int amount) {
        super(Constants.Zone.BATTLEFIELD, new EntersBattlefieldEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(amount))));
    }

    public ModularStaticAbility(final ModularStaticAbility ability) {
        super(ability);
    }

    @Override
    public ModularStaticAbility copy() {
        return new ModularStaticAbility(this);
    }

    @Override
    public String getRule() {
        return "";
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
        if (sourcePermanent != null) {
            int numberOfCounters = sourcePermanent.getCounters().getCount(CounterType.P1P1);
            if (numberOfCounters > 0) {
                Player player = game.getPlayer(source.getControllerId());
                if (player != null) {
                    Permanent targetArtifact = game.getPermanent(targetPointer.getFirst(game, source));
                    if (targetArtifact != null) {
                        targetArtifact.addCounters(CounterType.P1P1.createInstance(numberOfCounters), game);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

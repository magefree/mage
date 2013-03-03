package mage.abilities.effects.common.counter;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class RemoveCounterSourceEffect extends OneShotEffect<RemoveCounterSourceEffect> {
    private Counter counter;

    public RemoveCounterSourceEffect(Counter counter) {
        super(Constants.Outcome.UnboostCreature);
        this.counter = counter;
        setText();
    }

    public RemoveCounterSourceEffect(RemoveCounterSourceEffect effect) {
        super(effect);
        this.counter = effect.counter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null && p.getCounters().getCount(counter.getName()) >= counter.getCount()) {
            p.removeCounters(counter.getName(), counter.getCount(), game);
            return true;
        }
        Card c = game.getCard(source.getSourceId());
        if (c != null && c.getCounters().getCount(counter.getName()) >= counter.getCount()) {
            c.removeCounters(counter.getName(), counter.getCount(), game);
            game.informPlayers(new StringBuilder("Removed ").append(counter.getCount()).append(" ").append(counter.getName())
                    .append(" counter from ").append(c.getName())
                    .append(" (").append(c.getCounters().getCount(counter.getName())).append(" left)").toString());
            return true;
        }    
        return false;
    }

    @Override
    public RemoveCounterSourceEffect copy() {
        return new RemoveCounterSourceEffect(this);
    }

     private void setText() {
        if (counter.getCount() > 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("remove ").append(Integer.toString(counter.getCount())).append(" ").append(counter.getName()).append(" counters from {this}");
            staticText = sb.toString();
        } else
            staticText = "remove a " + counter.getName() + " counter from {this}";
    }
}

package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.counters.CounterType;
import mage.game.Game;

public class HaveCounter implements Condition {
	private CounterType counterType;

	public HaveCounter(CounterType type) {
		this.counterType = type;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return game.getPermanent(source.getSourceId()).getCounters().getCount(counterType) > 0;
	}
}

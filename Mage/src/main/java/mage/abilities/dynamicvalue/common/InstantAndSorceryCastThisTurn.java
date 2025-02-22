package mage.abilities.dynamicvalue.common;


import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.game.Game;
import mage.watchers.common.SpellsCastWatcher;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

public enum InstantAndSorceryCastThisTurn implements DynamicValue
{
	YOU("you've cast"),
	ALL("all players have cast"),
	OPPONENTS("your opponents have cast");

	private final String message;
	private final InstantAndSorceryCastThisTurnHint hint;

	InstantAndSorceryCastThisTurn(String message) {
		this.message = "Instant and sorcery spells " + message + " this turn";
		this.hint = new InstantAndSorceryCastThisTurnHint(this);
	}

	@Override
	public int calculate(Game game, Ability sourceAbility, Effect effect) {
		return getSpellsCastThisTurn(game, sourceAbility);
	}

	@Override
	public InstantAndSorceryCastThisTurn copy() {
		return this;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public Hint getHint() {
		return this.hint;
	}

	private int getSpellsCastThisTurn(Game game, Ability ability) {
		Collection<UUID> playerIds;
		switch (this) {
			case YOU:
				playerIds = Collections.singletonList(ability.getControllerId());
				break;
			case ALL:
				playerIds = game.getState().getPlayersInRange(ability.getControllerId(), game);
				break;
			case OPPONENTS:
				playerIds = game.getOpponents(ability.getControllerId());
				break;
			default:
				return 0;
		}
		SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
		if (watcher == null) {
			return 0;
		}
		return (int) playerIds.stream()
			.map(watcher::getSpellsCastThisTurn)
			.flatMap(Collection::stream)
			.filter(Objects::nonNull)
			.filter(spell -> spell.isInstantOrSorcery(game))
			.count();
	}
}

class InstantAndSorceryCastThisTurnHint implements Hint {
	InstantAndSorceryCastThisTurn value;

	public InstantAndSorceryCastThisTurnHint(InstantAndSorceryCastThisTurn value) {
		this.value = value;
	}

	@Override
	public String getText(Game game, Ability ability) {
		int count = value.calculate(game, ability, null);
		return value.getMessage() + ": " + count;
	}

	@Override
	public Hint copy() {
		return new InstantAndSorceryCastThisTurnHint(this.value);
	}
}

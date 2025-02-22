package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.CardsInExileCount;
import mage.constants.ComparisonType;
import mage.game.Game;


public class CardsInExileCondition implements Condition
{
	private final ComparisonType type;
	private final int count;
	private CardsInExileCount cardsInExileCount;

	public CardsInExileCondition(ComparisonType type, int count)
	{
		this(type, count, CardsInExileCount.YOU);
	}

	public CardsInExileCondition(ComparisonType type, int count, CardsInExileCount cardsInExileCount)
	{
		this.type = type;
		this.count = count;
		this.cardsInExileCount = cardsInExileCount;
	}

	@Override
	public boolean apply(Game game, Ability source)
	{
		int exileCards = cardsInExileCount.calculate(game, source, null);
		return ComparisonType.compare(exileCards, type, count);
	}
}

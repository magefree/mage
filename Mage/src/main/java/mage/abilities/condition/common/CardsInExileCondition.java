package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInExileCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.constants.ComparisonType;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * Cards in exile condition
 *
 * @author Jmlundeen
 */
public class CardsInExileCondition implements Condition {
	private final ComparisonType type;
	private final int count;
	private final DynamicValue cardsInExileCount;

	public CardsInExileCondition(ComparisonType type, int count) {
		this(type, count, CardsInExileCount.ALL);
	}

	public CardsInExileCondition(ComparisonType type, int count, DynamicValue cardsInExileCount) {
		this.type = type;
		this.count = count;
		this.cardsInExileCount = cardsInExileCount;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		int exileCards = cardsInExileCount.calculate(game, source, null);
		return ComparisonType.compare(exileCards, type, count);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("there are ");
		String countString = CardUtil.numberToText(count);
		switch (type) {
			case MORE_THAN:
				sb.append("more than ").append(countString).append(" ");
				break;
			case FEWER_THAN:
				sb.append("fewer than ").append(countString).append(" ");
				break;
			case OR_LESS:
				sb.append(countString).append(" or less ");
				break;
			case OR_GREATER:
				sb.append(countString).append(" or more ");
				break;
            default:
                throw new IllegalArgumentException("comparison rules for " + type + " missing");
		}
		sb.append("cards in exile");
		return sb.toString();
	}
}

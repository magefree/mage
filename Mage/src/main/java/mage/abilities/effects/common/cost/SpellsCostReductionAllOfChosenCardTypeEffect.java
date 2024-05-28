package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.game.Game;

/**
 * @author emerald000
 */
public class SpellsCostReductionAllOfChosenCardTypeEffect extends SpellsCostReductionAllEffect {

    public SpellsCostReductionAllOfChosenCardTypeEffect(FilterCard filter, int amount) {
        this(filter, amount, false);
    }

    public SpellsCostReductionAllOfChosenCardTypeEffect(FilterCard filter, int amount, boolean onlyControlled) {
        super(filter, amount, false, onlyControlled);
    }

    protected SpellsCostReductionAllOfChosenCardTypeEffect(final SpellsCostReductionAllOfChosenCardTypeEffect effect) {
        super(effect);
    }

    @Override
    public SpellsCostReductionAllOfChosenCardTypeEffect copy() {
        return new SpellsCostReductionAllOfChosenCardTypeEffect(this);
    }

    @Override
    protected boolean selectedByRuntimeData(Card card, Ability source, Game game) {
        Object savedType = game.getState().getValue(source.getSourceId() + "_type");
        if (savedType instanceof String) {
            return card.getCardType(game).contains(CardType.fromString((String) savedType));
        }
        return false;
    }
}

package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.ManaType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

/**
 * @author JayDi85
 */
public class ChromaCount implements DynamicValue {

    private final ManaType needManaType;

    public ChromaCount(ManaType needManaType) {
        this.needManaType = needManaType;
    }

    private ChromaCount(final ChromaCount dynamicValue) {
        super();
        this.needManaType = dynamicValue.needManaType;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int chroma = 0;
        for (Card card : game.getBattlefield().getAllActivePermanents(new FilterControlledPermanent(), sourceAbility.getControllerId(), game)) {
            chroma += card.getManaCost().getMana().get(this.needManaType);
        }
        return chroma;
    }

    @Override
    public ChromaCount copy() {
        return new ChromaCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "";
    }

}
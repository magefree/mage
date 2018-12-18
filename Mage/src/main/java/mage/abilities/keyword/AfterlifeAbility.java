package mage.abilities.keyword;

import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.game.permanent.token.WhiteBlackSpiritToken;
import mage.util.CardUtil;

public class AfterlifeAbility extends DiesTriggeredAbility {
    private final int tokenCount;

    public AfterlifeAbility(int tokenCount) {
        super(new CreateTokenEffect(new WhiteBlackSpiritToken(), tokenCount), false);
        this.tokenCount = tokenCount;
    }

    public AfterlifeAbility(final AfterlifeAbility ability) {
        super(ability);
        this.tokenCount = ability.tokenCount;
    }

    @Override
    public String getRule() {
        return "Afterlife " + tokenCount + " <i>(When this creature dies, create "
                + CardUtil.numberToText(tokenCount, "a")
                + " white and black Spirit creature token"
                + (tokenCount > 1 ? "s" : "")
                + " with flying)</i>";
    }

    @Override
    public AfterlifeAbility copy() {
        return new AfterlifeAbility(this);
    }
}

package mage.game.permanent.token;

import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class JacePlaneswalkerToken extends TokenImpl {

    public JacePlaneswalkerToken() {
        super("Jace", "blue Jace planeswalker token with \"-1: Surveil 1\" and \"-3: Draw a card.\"");
        this.cardType.add(CardType.PLANESWALKER);
        this.subtype.add(SubType.JACE);
        this.color.setBlue(true);
        this.startingLoyalty = 0;

        this.addAbility(new LoyaltyAbility(new SurveilEffect(1), -1));
        this.addAbility(new LoyaltyAbility(new DrawCardSourceControllerEffect(1), -3));
    }

    private JacePlaneswalkerToken(final JacePlaneswalkerToken token) {
        super(token);
    }

    public JacePlaneswalkerToken copy() {
        return new JacePlaneswalkerToken(this);
    }
}

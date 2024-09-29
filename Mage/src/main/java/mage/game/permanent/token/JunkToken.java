package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class JunkToken extends TokenImpl {

    public JunkToken() {
        super("Junk Token", "Junk token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.JUNK);

        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn), new TapSourceCost()
        );
        ability.addCost(new SacrificeSourceCost().setText("sacrifice this artifact"));
        this.addAbility(ability);
    }

    private JunkToken(final JunkToken token) {
        super(token);
    }

    public JunkToken copy() {
        return new JunkToken(this);
    }
}

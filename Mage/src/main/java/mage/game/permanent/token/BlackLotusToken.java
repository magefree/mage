package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class BlackLotusToken extends TokenImpl {

    public BlackLotusToken() {
        super("Black Lotus", "Black Lotus token");
        manaCost = new ManaCostsImpl<>("{0}");
        cardType.add(CardType.ARTIFACT);

        Ability ability = new SimpleManaAbility(new AddManaOfAnyColorEffect(3), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BlackLotusToken(final BlackLotusToken token) {
        super(token);
    }

    public BlackLotusToken copy() {
        return new BlackLotusToken(this);
    }
}

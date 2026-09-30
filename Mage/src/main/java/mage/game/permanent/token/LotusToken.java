package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class LotusToken extends TokenImpl {

    public LotusToken() {
        super("Lotus", "Lotus token");
        cardType.add(CardType.ARTIFACT);

        Ability ability = new SimpleManaAbility(new AddManaOfAnyColorEffect(3), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private LotusToken(final LotusToken token) {
        super(token);
    }

    public LotusToken copy() {
        return new LotusToken(this);
    }
}

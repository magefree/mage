package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class BananaToken extends TokenImpl {

    public BananaToken() {
        super("Banana", "colorless artifact token named Banana with \"{T}, Sacrifice this artifact: Add {R} or {G}. You gain 2 life.\"");
        cardType.add(CardType.ARTIFACT);

        // {T}, Sacrifice this artifact: Add {R} or {G}. You gain 2 life.
        Ability ability = new RedManaAbility();
        ability.addCost(new SacrificeSourceCost().setText("sacrifice this artifact"));
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);
        ability = new GreenManaAbility();
        ability.addCost(new SacrificeSourceCost().setText("sacrifice this artifact"));
        ability.addEffect(new GainLifeEffect(2));
        this.addAbility(ability);
    }

    public BananaToken(final BananaToken token) {
        super(token);
    }

    public BananaToken copy() {
        return new BananaToken(this);
    }
}

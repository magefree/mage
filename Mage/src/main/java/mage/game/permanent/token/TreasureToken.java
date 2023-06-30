package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public final class TreasureToken extends TokenImpl {

    public TreasureToken() {
        super("Treasure Token", "Treasure token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.TREASURE);

        // {T}, Sacrifice this artifact: Add one mana of any color.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost().setText("sacrifice this artifact"));
        this.addAbility(ability);
    }

    public TreasureToken(final TreasureToken token) {
        super(token);
    }

    public TreasureToken copy() {
        return new TreasureToken(this);
    }
}

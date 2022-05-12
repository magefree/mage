

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.constants.Zone;

/**
 *
 * @author spjspj
 */
public final class EtheriumCellToken extends TokenImpl {

    public EtheriumCellToken() {
        super("Etherium Cell", "colorless artifact token named Etherium Cell with \"{T}, Sacrifice this artifact: Add one mana of any color.\"");
        this.setOriginalExpansionSetCode("AER");
        cardType.add(CardType.ARTIFACT);

        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());

        this.addAbility(ability);
    }

    public EtheriumCellToken(final EtheriumCellToken token) {
        super(token);
    }

    public EtheriumCellToken copy() {
        return new EtheriumCellToken(this);
    }
}

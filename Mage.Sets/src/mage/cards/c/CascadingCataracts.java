
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.AddManaInAnyCombinationEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author spjspj
 */
public final class CascadingCataracts extends CardImpl {

    public CascadingCataracts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Indestructibles
        this.addAbility(IndestructibleAbility.getInstance());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {5}, {T}: Add five mana in any combination of colors.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaInAnyCombinationEffect(5), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CascadingCataracts(final CascadingCataracts card) {
        super(card);
    }

    @Override
    public CascadingCataracts copy() {
        return new CascadingCataracts(this);
    }
}

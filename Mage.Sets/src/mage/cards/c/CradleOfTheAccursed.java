
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.ZombieToken;

/**
 *
 * @author spjspj
 */
public final class CradleOfTheAccursed extends CardImpl {

    public CradleOfTheAccursed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}, Sacrifice Cradle of the Accursed: Create a 2/2 black Zombie creature token. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieToken(), 1), new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private CradleOfTheAccursed(final CradleOfTheAccursed card) {
        super(card);
    }

    @Override
    public CradleOfTheAccursed copy() {
        return new CradleOfTheAccursed(this);
    }
}

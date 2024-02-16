
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.EldraziScionToken;

/**
 *
 * @author fireshoes
 */
public final class SpawningBed extends CardImpl {

    public SpawningBed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {6}, {T}, Sacrifice Spawning Bed: Create three 1/1 colorless Eldrazi Scion creature tokens. They have "Sacrifice this creature: Add {C}."
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new EldraziScionToken(), 3), new ManaCostsImpl<>("{6}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SpawningBed(final SpawningBed card) {
        super(card);
    }

    @Override
    public SpawningBed copy() {
        return new SpawningBed(this);
    }
}

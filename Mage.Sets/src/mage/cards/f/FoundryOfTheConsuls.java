
package mage.cards.f;

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
import mage.game.permanent.token.ThopterColorlessToken;

/**
 *
 * @author LoneFox
 *
 */
public final class FoundryOfTheConsuls extends CardImpl {

    public FoundryOfTheConsuls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {5}, {T}, Sacrifice Foundry of the Consuls: Create two 1/1 colorless Thopter artifact creature tokens with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ThopterColorlessToken(), 2),
                new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private FoundryOfTheConsuls(final FoundryOfTheConsuls card) {
        super(card);
    }

    @Override
    public FoundryOfTheConsuls copy() {
        return new FoundryOfTheConsuls(this);
    }
}

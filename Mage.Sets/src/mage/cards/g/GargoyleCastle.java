
package mage.cards.g;

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
import mage.game.permanent.token.GargoyleToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class GargoyleCastle extends CardImpl {

    public GargoyleCastle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);
        // {T}: Add {C}.

        this.addAbility(new ColorlessManaAbility());
        // {T}, {5}, Sacrifice Gargoyle Castle: Put a 3/4 colorless Gargoyle artifact creature token with flying onto the battlefield.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new GargoyleToken()),
                new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private GargoyleCastle(final GargoyleCastle card) {
        super(card);
    }

    @Override
    public GargoyleCastle copy() {
        return new GargoyleCastle(this);
    }

}
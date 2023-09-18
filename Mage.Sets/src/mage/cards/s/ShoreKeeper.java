
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class ShoreKeeper extends CardImpl {

    public ShoreKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.TRILOBITE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // {7}{u}, {t}, Sacrifice Shore Keeper: Draw three cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(3), new ManaCostsImpl<>("{7}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ShoreKeeper(final ShoreKeeper card) {
        super(card);
    }

    @Override
    public ShoreKeeper copy() {
        return new ShoreKeeper(this);
    }
}

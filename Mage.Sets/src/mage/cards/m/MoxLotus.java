
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author spjspj/psjpsj
 */
public final class MoxLotus extends CardImpl {

    public MoxLotus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{15}");

        // {t}: Add infinity (or 1*10^9 to account for a potential mana reflection).        
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(1000000000), new TapSourceCost()));

        // {100}: Add one mana of any color.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(1), new ManaCostsImpl<>("{100}"));
        this.addAbility(ability);

        // You don't lose life due to mana burn.
        // Situation normal??
    }

    private MoxLotus(final MoxLotus card) {
        super(card);
    }

    @Override
    public MoxLotus copy() {
        return new MoxLotus(this);
    }
}
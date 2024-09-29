
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class ThroneOfTheHighCity extends CardImpl {

    public ThroneOfTheHighCity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}, {T}, Sacrifice Throne of the High City: You become the monarch.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesMonarchSourceEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addHint(MonarchHint.instance);
        this.addAbility(ability);
    }

    private ThroneOfTheHighCity(final ThroneOfTheHighCity card) {
        super(card);
    }

    @Override
    public ThroneOfTheHighCity copy() {
        return new ThroneOfTheHighCity(this);
    }
}

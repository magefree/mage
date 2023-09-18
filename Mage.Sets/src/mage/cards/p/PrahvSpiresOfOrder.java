
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PreventDamageBySourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class PrahvSpiresOfOrder extends CardImpl {

    public PrahvSpiresOfOrder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {4}{W}{U}, {T}: Prevent all damage a source of your choice would deal this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageBySourceEffect(), new ManaCostsImpl<>("{4}{W}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private PrahvSpiresOfOrder(final PrahvSpiresOfOrder card) {
        super(card);
    }

    @Override
    public PrahvSpiresOfOrder copy() {
        return new PrahvSpiresOfOrder(this);
    }
}

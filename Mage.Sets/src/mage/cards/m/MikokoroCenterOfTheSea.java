
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class MikokoroCenterOfTheSea extends CardImpl {

    public MikokoroCenterOfTheSea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.LEGENDARY);
        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {2}, {tap}: Each player draws a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardAllEffect(1), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MikokoroCenterOfTheSea(final MikokoroCenterOfTheSea card) {
        super(card);
    }

    @Override
    public MikokoroCenterOfTheSea copy() {
        return new MikokoroCenterOfTheSea(this);
    }
}

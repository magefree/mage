
package mage.cards.b;

import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class BreedingPool extends CardImpl {

    public BreedingPool(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);
        this.subtype.add(SubType.FOREST, SubType.ISLAND);

        this.addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new PayLifeCost(2)), "you may pay 2 life. If you don't, it enters the battlefield tapped"));
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    public BreedingPool(final BreedingPool card) {
        super(card);
    }

    @Override
    public BreedingPool copy() {
        return new BreedingPool(this);
    }

}

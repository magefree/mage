
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author North
 */
public final class HandOfEmrakul extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Eldrazi Spawn");

    static {
        filter.add(new NamePredicate("Eldrazi Spawn"));
    }

    public HandOfEmrakul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{9}");
        this.subtype.add(SubType.ELDRAZI);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // You may sacrifice four Eldrazi Spawn rather than pay Hand of Emrakul's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(new TargetControlledPermanent(4, 4, filter, true))));
        // Annihilator 1
        this.addAbility(new AnnihilatorAbility(1));
    }

    private HandOfEmrakul(final HandOfEmrakul card) {
        super(card);
    }

    @Override
    public HandOfEmrakul copy() {
        return new HandOfEmrakul(this);
    }
}

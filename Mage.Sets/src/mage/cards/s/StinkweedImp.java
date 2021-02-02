
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class StinkweedImp extends CardImpl {

    public StinkweedImp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.IMP);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Stinkweed Imp deals combat damage to a creature, destroy that creature.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(new DestroyTargetEffect(), true, false, true));
        // Dredge 5
        this.addAbility(new DredgeAbility(5));
    }

    private StinkweedImp(final StinkweedImp card) {
        super(card);
    }

    @Override
    public StinkweedImp copy() {
        return new StinkweedImp(this);
    }
}

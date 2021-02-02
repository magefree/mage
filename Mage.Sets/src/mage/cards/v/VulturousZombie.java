
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutCardIntoGraveFromAnywhereAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class VulturousZombie extends CardImpl {

    public VulturousZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a card is put into an opponent's graveyard from anywhere, put a +1/+1 counter on Vulturous Zombie.
        this.addAbility(new PutCardIntoGraveFromAnywhereAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, TargetController.OPPONENT));
    }

    private VulturousZombie(final VulturousZombie card) {
        super(card);
    }

    @Override
    public VulturousZombie copy() {
        return new VulturousZombie(this);
    }
}

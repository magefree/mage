package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.ExpendTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WandertaleMentor extends CardImpl {

    public WandertaleMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you expend 4, put a +1/+1 counter on Wandertale Mentor.
        this.addAbility(new ExpendTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), 4
        ));

        // {T}: Add {R} or {G}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private WandertaleMentor(final WandertaleMentor card) {
        super(card);
    }

    @Override
    public WandertaleMentor copy() {
        return new WandertaleMentor(this);
    }
}

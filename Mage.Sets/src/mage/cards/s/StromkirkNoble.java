package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author Alvin
 * @author ayratn
 */
public final class StromkirkNoble extends CardImpl {

    public StromkirkNoble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.VAMPIRE, SubType.NOBLE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Stromkirk Noble can't be blocked by Humans.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(new FilterCreaturePermanent(SubType.HUMAN, "Humans"), Duration.WhileOnBattlefield)));

        // Whenever Stromkirk Noble deals combat damage to a player, put a +1/+1 counter on it.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
    }

    private StromkirkNoble(final StromkirkNoble card) {
        super(card);
    }

    @Override
    public StromkirkNoble copy() {
        return new StromkirkNoble(this);
    }
}

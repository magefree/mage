package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.DamageEachOtherOpponentThatMuchEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.DoubleCountersSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author muz
 */
public final class GargantosTheEndbringer extends CardImpl {

    public GargantosTheEndbringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Gargantos enters with three +1/+1 counters on him.
        this.addAbility(new EntersBattlefieldAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
            "with three +1/+1 counters on him"
        ));

        // At the beginning of your upkeep, double the number of +1/+1 counters on Gargantos.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoubleCountersSourceEffect(CounterType.P1P1)));

        // Whenever Gargantos deals combat damage to an opponent, he deals that much damage to each other opponent.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(
            new DamageEachOtherOpponentThatMuchEffect(), false, true, true
        ));
    }

    private GargantosTheEndbringer(final GargantosTheEndbringer card) {
        super(card);
    }

    @Override
    public GargantosTheEndbringer copy() {
        return new GargantosTheEndbringer(this);
    }
}

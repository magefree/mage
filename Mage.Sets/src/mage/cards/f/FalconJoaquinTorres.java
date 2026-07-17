package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.abilities.Ability;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FalconJoaquinTorres extends CardImpl {

    public FalconJoaquinTorres(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Battalion -- Whenever Falcon and at least two other creatures attack, put a +1/+1 counter on him and scry 1.
        Ability ability = new BattalionAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new ScryEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private FalconJoaquinTorres(final FalconJoaquinTorres card) {
        super(card);
    }

    @Override
    public FalconJoaquinTorres copy() {
        return new FalconJoaquinTorres(this);
    }
}

package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.permanent.token.BirdToken;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FalconAndRedwing extends CardImpl {

    public FalconAndRedwing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Falcon and Redwing deal combat damage to a player, create that many 1/1 white Bird creature tokens with flying, then put a +1/+1 counter on Falcon and Redwing.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
            new CreateTokenEffect(new BirdToken(), SavedDamageValue.MANY), false, false
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy(", then"));
        this.addAbility(ability);
    }

    private FalconAndRedwing(final FalconAndRedwing card) {
        super(card);
    }

    @Override
    public FalconAndRedwing copy() {
        return new FalconAndRedwing(this);
    }
}

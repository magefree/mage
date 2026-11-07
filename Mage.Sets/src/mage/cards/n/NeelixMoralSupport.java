package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class NeelixMoralSupport extends CardImpl {

    public NeelixMoralSupport(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TALAXIAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Neelix enters, create a Food token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FoodToken())));

        // Whenever you gain life, put a +1/+1 counter on Neelix.
        this.addAbility(new GainLifeControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private NeelixMoralSupport(final NeelixMoralSupport card) {
        super(card);
    }

    @Override
    public NeelixMoralSupport copy() {
        return new NeelixMoralSupport(this);
    }
}

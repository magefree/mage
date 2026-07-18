package mage.cards.a;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.command.emblems.AjaniResoluteEmblem;
import mage.game.permanent.token.AjanisPridemateToken;
import java.util.UUID;

/**
 * @author muz
 */
public final class AjaniResolute extends CardImpl {

    public AjaniResolute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AJANI);

        this.setStartingLoyalty(2);

        // Whenever you gain life, put a loyalty counter on Ajani.
        this.addAbility(new GainLifeControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance())));

        // 0: You gain 1 life.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(1), 0));

        // -4: Create a 2/2 white Cat Soldier creature token named Ajani's Pridemate. It has "Whenever you gain life, put a +1/+1 counter on this token."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new AjanisPridemateToken()), -4));

        // -10: You get an emblem with "Creatures you control get +2/+2"
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new AjaniResoluteEmblem()), -10));
    }

    private AjaniResolute(final AjaniResolute card) {
        super(card);
    }

    @Override
    public AjaniResolute copy() {
        return new AjaniResolute(this);
    }
}

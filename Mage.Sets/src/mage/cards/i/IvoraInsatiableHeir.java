package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.token.BloodToken;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class IvoraInsatiableHeir extends CardImpl {

    public IvoraInsatiableHeir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Ivora, Insatiable Heir enters and whenever it deals combat damage to a player, create a Blood token.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new BloodToken()),
                new EntersBattlefieldTriggeredAbility(null, false),
                new DealsCombatDamageToAPlayerTriggeredAbility(null, false)
        ));

        // Whenever you discard a card, put a +1/+1 counter on Ivora.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false));
    }

    private IvoraInsatiableHeir(final IvoraInsatiableHeir card) {
        super(card);
    }

    @Override
    public IvoraInsatiableHeir copy() {
        return new IvoraInsatiableHeir(this);
    }
}

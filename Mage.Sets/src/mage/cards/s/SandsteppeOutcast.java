package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class SandsteppeOutcast extends CardImpl {

    public SandsteppeOutcast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Sandsteppe Outcast enters the battlefield, choose one -
        // * Put a +1/+1 counter on Sandsteppe Outcast.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));

        // * Create a 1/1 white Spirit creature token with flying. 
        Mode mode = new Mode(new CreateTokenEffect(new SpiritWhiteToken()));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private SandsteppeOutcast(final SandsteppeOutcast card) {
        super(card);
    }

    @Override
    public SandsteppeOutcast copy() {
        return new SandsteppeOutcast(this);
    }
}

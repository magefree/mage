
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.WolfToken;

/**
 *
 * @author North
 */
public final class TurntimberRanger extends CardImpl {

    public TurntimberRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.subtype.add(SubType.RANGER);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Turntimber Ranger or another Ally enters the battlefield under your control, you may create a 2/2 green Wolf creature token. If you do, put a +1/+1 counter on Turntimber Ranger.
        Ability ability = new AllyEntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WolfToken()), true);
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("If you do,"));
        this.addAbility(ability.setAbilityWord(null));
    }

    private TurntimberRanger(final TurntimberRanger card) {
        super(card);
    }

    @Override
    public TurntimberRanger copy() {
        return new TurntimberRanger(this);
    }
}

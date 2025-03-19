package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.RenewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgentOfKotis extends CardImpl {

    public AgentOfKotis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Renew -- {3}{U}, Exile this card from your graveyard: Put two +1/+1 counters on target creature. Activate only as a sorcery.
        this.addAbility(new RenewAbility("{3}{U}", CounterType.P1P1.createInstance(2)));
    }

    private AgentOfKotis(final AgentOfKotis card) {
        super(card);
    }

    @Override
    public AgentOfKotis copy() {
        return new AgentOfKotis(this);
    }
}

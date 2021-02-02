package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class WorldsoulColossus extends CardImpl {

    public WorldsoulColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{W}");
        
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Worldsoul Colossus enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(
                        CounterType.P1P1.createInstance()
                )
        ));
    }

    private WorldsoulColossus(final WorldsoulColossus card) {
        super(card);
    }

    @Override
    public WorldsoulColossus copy() {
        return new WorldsoulColossus(this);
    }
}

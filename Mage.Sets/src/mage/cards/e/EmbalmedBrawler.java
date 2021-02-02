
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.AmplifyEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.AmplifyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LoneFox
 */
public final class EmbalmedBrawler extends CardImpl {

    public EmbalmedBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Amplify 1
        this.addAbility(new AmplifyAbility(AmplifyEffect.AmplifyFactor.Amplify1));
        // Whenever Embalmed Brawler attacks or blocks, you lose 1 life for each +1/+1 counter on it.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new LoseLifeSourceControllerEffect(new CountersSourceCount(CounterType.P1P1)), false));
    }

    private EmbalmedBrawler(final EmbalmedBrawler card) {
        super(card);
    }

    @Override
    public EmbalmedBrawler copy() {
        return new EmbalmedBrawler(this);
    }
}

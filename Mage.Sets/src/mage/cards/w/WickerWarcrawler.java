
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author jeffwadsworth
 */
public final class WickerWarcrawler extends CardImpl {

    public WickerWarcrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Wicker Warcrawler attacks or blocks, put a -1/-1 counter on it at end of combat.
        Effect effect = new AddCountersSourceEffect(CounterType.M1M1.createInstance(), true);
        effect.setText("put a -1/-1 counter on it at end of combat");
        DelayedTriggeredAbility ability = new AtTheEndOfCombatDelayedTriggeredAbility(effect);
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(ability, false, false), false));

    }

    private WickerWarcrawler(final WickerWarcrawler card) {
        super(card);
    }

    @Override
    public WickerWarcrawler copy() {
        return new WickerWarcrawler(this);
    }
}

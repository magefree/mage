
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreatureExploresTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class WildgrowthWalker extends CardImpl {

    public WildgrowthWalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever a creature you control explores, put a +1/+1 counter on Wildgrowth Walker and you gain 3 life.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance());
        Ability ability = new CreatureExploresTriggeredAbility(effect);
        effect = new GainLifeEffect(3);
        effect.setText("and you gain 3 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private WildgrowthWalker(final WildgrowthWalker card) {
        super(card);
    }

    @Override
    public WildgrowthWalker copy() {
        return new WildgrowthWalker(this);
    }
}


package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VanishingSacrificeAbility;
import mage.abilities.keyword.VanishingUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SetTargetPointer;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class LavacoreElemental extends CardImpl {

    public LavacoreElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Vanishing 1
        Ability ability = new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.TIME.createInstance(1)));
        ability.setRuleVisible(false);
        this.addAbility(ability);
        this.addAbility(new VanishingUpkeepAbility(1));
        this.addAbility(new VanishingSacrificeAbility());

        // Whenever a creature you control deals combat damage to a player, put a time counter on Lavacore Elemental.
        Effect effect = new AddCountersSourceEffect(CounterType.TIME.createInstance(1));
        effect.setText("put a time counter on {this}");
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                effect,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, false, SetTargetPointer.PERMANENT, true
        ));
    }

    private LavacoreElemental(final LavacoreElemental card) {
        super(card);
    }

    @Override
    public LavacoreElemental copy() {
        return new LavacoreElemental(this);
    }
}


package mage.cards.w;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.CycleAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class WitheringHex extends CardImpl {

    public WitheringHex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever a player cycles a card, put a plague counter on Withering Hex.
        this.addAbility(new CycleAllTriggeredAbility(new AddCountersSourceEffect(CounterType.PLAGUE.createInstance()), false));

        // Enchanted creature gets -1/-1 for each plague counter on Withering Hex.
        DynamicValue value = new MultipliedValue(new CountersSourceCount(CounterType.PLAGUE), -1);
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostEnchantedEffect(value, value, Duration.WhileOnBattlefield)
                        .setText("Enchanted creature gets -1/-1 for each plague counter on {this}.")
        ));
    }

    private WitheringHex(final WitheringHex card) {
        super(card);
    }

    @Override
    public WitheringHex copy() {
        return new WitheringHex(this);
    }
}

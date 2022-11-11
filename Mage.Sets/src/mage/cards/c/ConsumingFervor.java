
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ConsumingFervor extends CardImpl {

    public ConsumingFervor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +3/+3 and has "At the beginning of your upkeep, put a -1/-1 counter on this creature."
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3, 3, Duration.WhileOnBattlefield));
        Ability grantedAbility = new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance(1)), TargetController.YOU, false);
        Effect effect = new GainAbilityAttachedEffect(grantedAbility, AttachmentType.AURA);
        effect.setText("and has \"At the beginning of each upkeep, put a -1/-1 counter on this creature.\"");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private ConsumingFervor(final ConsumingFervor card) {
        super(card);
    }

    @Override
    public ConsumingFervor copy() {
        return new ConsumingFervor(this);
    }
}

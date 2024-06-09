package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.AttachedPermanentPowerCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class Necrosynthesis extends CardImpl {

    public Necrosynthesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has "Whenever another creature dies, put a +1/+1 counter on this creature."
        Effect effect = new GainAbilityAttachedEffect(new DiesCreatureTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false), AttachmentType.AURA);
        effect.setText("Enchanted creature has \"Whenever another creature dies, put a +1/+1 counter on this creature.\"");
        this.addAbility(new SimpleStaticAbility(effect));

        // When enchanted creature dies, look at the top X cards of your library, where X is its power.
        // Put one of those cards into your hand and the rest on the bottom of your library in a random order.
        effect = new LookLibraryAndPickControllerEffect(AttachedPermanentPowerCount.instance, 1, PutCards.HAND, PutCards.BOTTOM_RANDOM);
        effect.setText("look at the top X cards of your library, where X is its power. " +
                "Put one of those cards into your hand and the rest on the bottom of your library in a random order");
        ability = new DiesAttachedTriggeredAbility(effect, "enchanted creature");
        this.addAbility(ability);
    }

    private Necrosynthesis(final Necrosynthesis card) {
        super(card);
    }

    @Override
    public Necrosynthesis copy() {
        return new Necrosynthesis(this);
    }
}

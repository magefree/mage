package mage.cards.w;

import java.util.UUID;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.InspiredAbility;
import mage.constants.AttachmentType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author Cguy7777
 */
public final class WellRested extends CardImpl {

    public WellRested(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has "Whenever this creature becomes untapped, put two +1/+1 counters on it,
        // then you gain 2 life and draw a card. This ability triggers only once each turn."
        InspiredAbility gainedAbility = new InspiredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), false, false);
        gainedAbility.addEffect(new GainLifeEffect(2).concatBy(", then"));
        gainedAbility.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        gainedAbility.setTriggersOnceEachTurn(true);

        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA)
                .setText("Enchanted creature has \"Whenever this creature becomes untapped, " +
                        "put two +1/+1 counters on it, then you gain 2 life and draw a card. " +
                        "This ability triggers only once each turn.\"")));
    }

    private WellRested(final WellRested card) {
        super(card);
    }

    @Override
    public WellRested copy() {
        return new WellRested(this);
    }
}

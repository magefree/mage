package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.AttachEffect;
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
 * @author TheElk801
 */
public final class LightOfPromise extends CardImpl {

    public LightOfPromise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has "Whenever you gain life, put that many +1/+1 counters on this creature."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new GainLifeControllerTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance(), SavedGainedLifeValue.MANY),
                        false, true
                ), AttachmentType.AURA, Duration.WhileOnBattlefield, "Enchanted creature has \"Whenever you gain life, put that many +1/+1 counters on this creature.\""
        )));
    }

    private LightOfPromise(final LightOfPromise card) {
        super(card);
    }

    @Override
    public LightOfPromise copy() {
        return new LightOfPromise(this);
    }
}
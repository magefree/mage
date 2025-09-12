package mage.cards.s;

import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DoubleCountersTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
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
public final class SeismicTutelage extends CardImpl {

    public SeismicTutelage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, put a +1/+1 counter on enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAttachedEffect(CounterType.P1P1.createInstance(), "enchanted creature")
        ));

        // Whenever enchanted creature attacks, double the number of +1/+1 counters on it.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new DoubleCountersTargetEffect(CounterType.P1P1),
                AttachmentType.AURA, false, SetTargetPointer.PERMANENT
        ));
    }

    private SeismicTutelage(final SeismicTutelage card) {
        super(card);
    }

    @Override
    public SeismicTutelage copy() {
        return new SeismicTutelage(this);
    }
}

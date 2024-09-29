
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.Effect;
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
 * @author LevelX2
 */
public final class Sunbond extends CardImpl {

    public Sunbond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature has "Whenever you gain life, put that many +1/+1 counters on this creature."
        Effect effect = new GainAbilityAttachedEffect(
                new GainLifeControllerTriggeredAbility(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance(), SavedGainedLifeValue.MANY)
                                .setText("put that many +1/+1 counters on this creature"),
                        false, true
                ), AttachmentType.AURA, Duration.WhileOnBattlefield
        );
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    private Sunbond(final Sunbond card) {
        super(card);
    }

    @Override
    public Sunbond copy() {
        return new Sunbond(this);
    }
}
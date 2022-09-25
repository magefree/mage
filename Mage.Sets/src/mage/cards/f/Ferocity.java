package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.BlocksOrBlockedAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class Ferocity extends CardImpl {

    public Ferocity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever enchanted creature blocks or becomes blocked, you may put a +1/+1 counter on it.
        this.addAbility(new BlocksOrBlockedAttachedTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it"),
                true
        ));
    }

    private Ferocity(final Ferocity card) {
        super(card);
    }

    @Override
    public Ferocity copy() {
        return new Ferocity(this);
    }
}

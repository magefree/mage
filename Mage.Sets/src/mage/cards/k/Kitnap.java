package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.GiftWasPromisedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.GiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.GiftType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Kitnap extends CardImpl {

    public Kitnap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Gift a card
        this.addAbility(new GiftAbility(this, GiftType.CARD));

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Kitnap enters, tap enchanted creature. If the gift wasn't promised, put three stun counters on it.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect());
        ability.addEffect(new ConditionalOneShotEffect(
                new AddCountersAttachedEffect(CounterType.STUN.createInstance(3), ""),
                GiftWasPromisedCondition.FALSE, "if the gift wasn't promised, put three stun counters on it"
        ));
        this.addAbility(ability);

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(new ControlEnchantedEffect()));
    }

    private Kitnap(final Kitnap card) {
        super(card);
    }

    @Override
    public Kitnap copy() {
        return new Kitnap(this);
    }
}

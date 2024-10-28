package mage.cards.f;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class Feedback extends CardImpl {

    public Feedback(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant enchantment
        TargetPermanent auraTarget = new TargetEnchantmentPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of the upkeep of enchanted enchantment's controller, Feedback deals 1 damage to that player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(TargetController.CONTROLLER_ATTACHED_TO, new DamageTargetEffect(1).withTargetDescription("that player"),
                false
        ).setTriggerPhrase("At the beginning of the upkeep of enchanted enchantment's controller, "));
    }

    private Feedback(final Feedback card) {
        super(card);
    }

    @Override
    public Feedback copy() {
        return new Feedback(this);
    }
}

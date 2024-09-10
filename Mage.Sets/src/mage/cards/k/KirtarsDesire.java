package mage.cards.k;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackAttachedEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KirtarsDesire extends CardImpl {

    public KirtarsDesire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature can't attack.
        this.addAbility(new SimpleStaticAbility(new CantAttackAttachedEffect(AttachmentType.AURA)));

        // Threshold - Enchanted creature can't block as long as seven or more cards are in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantAttackBlockAttachedEffect(AttachmentType.AURA), ThresholdCondition.instance,
                "Enchanted creature can't block as long as seven or more cards are in your graveyard"
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private KirtarsDesire(final KirtarsDesire card) {
        super(card);
    }

    @Override
    public KirtarsDesire copy() {
        return new KirtarsDesire(this);
    }
}

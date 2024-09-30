package mage.cards.s;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class SetonsDesire extends CardImpl {

    public SetonsDesire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(2, 2)));

        // Threshold - As long as seven or more cards are in your graveyard, all creatures able to block enchanted creature do so.
        this.addAbility(new SimpleStaticAbility(new ConditionalRequirementEffect(
                new MustBeBlockedByAllAttachedEffect(AttachmentType.AURA), ThresholdCondition.instance
        ).setText("As long as seven or more cards are in your graveyard, all creatures able to block enchanted creature do so")).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private SetonsDesire(final SetonsDesire card) {
        super(card);
    }

    @Override
    public SetonsDesire copy() {
        return new SetonsDesire(this);
    }
}

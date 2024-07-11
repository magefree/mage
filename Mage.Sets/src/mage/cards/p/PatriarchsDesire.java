package mage.cards.p;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class PatriarchsDesire extends CardImpl {

    public PatriarchsDesire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +2/-2.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(2, -2)));

        // Threshold - Enchanted creature gets an additional +2/-2 as long as seven or more cards are in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEnchantedEffect(2, -2, Duration.WhileOnBattlefield), ThresholdCondition.instance,
                "enchanted creature gets an additional +2/-2 as long as seven or more cards are in your graveyard."
        )).setAbilityWord(AbilityWord.THRESHOLD));
    }

    private PatriarchsDesire(final PatriarchsDesire card) {
        super(card);
    }

    @Override
    public PatriarchsDesire copy() {
        return new PatriarchsDesire(this);
    }
}

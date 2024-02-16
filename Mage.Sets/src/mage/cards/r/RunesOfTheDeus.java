
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedCreatureColorCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class RunesOfTheDeus extends CardImpl {

    public RunesOfTheDeus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R/G}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // As long as enchanted creature is red, it gets +1/+1 and has double strike.
        SimpleStaticAbility redAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostEnchantedEffect(1, 1), new EnchantedCreatureColorCondition(ObjectColor.RED), "As long as enchanted creature is red, it gets +1/+1"));
        redAbility.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(DoubleStrikeAbility.getInstance(), AttachmentType.AURA), new EnchantedCreatureColorCondition(ObjectColor.RED), "and has double strike"));
        this.addAbility(redAbility);
        // As long as enchanted creature is green, it gets +1/+1 and has trample.
        SimpleStaticAbility greenAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostEnchantedEffect(1, 1), new EnchantedCreatureColorCondition(ObjectColor.GREEN), "As long as enchanted creature is green, it gets +1/+1"));
        greenAbility.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA), new EnchantedCreatureColorCondition(ObjectColor.GREEN), "and has trample"));
        this.addAbility(greenAbility);
    }

    private RunesOfTheDeus(final RunesOfTheDeus card) {
        super(card);
    }

    @Override
    public RunesOfTheDeus copy() {
        return new RunesOfTheDeus(this);
    }
}

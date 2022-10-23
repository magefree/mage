
package mage.cards.f;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedCreatureColorCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
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
public final class FavorOfTheOverbeing extends CardImpl {

    public FavorOfTheOverbeing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G/U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // As long as enchanted creature is green, it gets +1/+1 and has vigilance.
        SimpleStaticAbility greenAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostEnchantedEffect(1, 1), new EnchantedCreatureColorCondition(ObjectColor.GREEN), "As long as enchanted creature is green, it gets +1/+1"));
        greenAbility.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA), new EnchantedCreatureColorCondition(ObjectColor.GREEN), "and has vigilance"));
        this.addAbility(greenAbility);
        // As long as enchanted creature is blue, it gets +1/+1 and has flying.
        SimpleStaticAbility blueAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostEnchantedEffect(1, 1), new EnchantedCreatureColorCondition(ObjectColor.BLUE), "As long as enchanted creature is blue, it gets +1/+1"));
        blueAbility.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA), new EnchantedCreatureColorCondition(ObjectColor.BLUE), "and has flying"));
        this.addAbility(blueAbility);
    }

    private FavorOfTheOverbeing(final FavorOfTheOverbeing card) {
        super(card);
    }

    @Override
    public FavorOfTheOverbeing copy() {
        return new FavorOfTheOverbeing(this);
    }
}

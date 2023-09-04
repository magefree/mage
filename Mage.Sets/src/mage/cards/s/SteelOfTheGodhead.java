
package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedCreatureColorCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalEvasionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public final class SteelOfTheGodhead extends CardImpl {

    public SteelOfTheGodhead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W/U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // As long as enchanted creature is white, it gets +1/+1 and has lifelink.
        SimpleStaticAbility whiteAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostEnchantedEffect(1, 1), new EnchantedCreatureColorCondition(ObjectColor.WHITE), "As long as enchanted creature is white, it gets +1/+1"));
        whiteAbility.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.AURA), new EnchantedCreatureColorCondition(ObjectColor.WHITE), "and has lifelink"));
        this.addAbility(whiteAbility);
        // As long as enchanted creature is blue, it gets +1/+1 and can't be blocked.
        SimpleStaticAbility blueAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostEnchantedEffect(1, 1), new EnchantedCreatureColorCondition(ObjectColor.BLUE), "As long as enchanted creature is blue, it gets +1/+1"));
        Effect effect = new ConditionalEvasionEffect(new CantBeBlockedAttachedEffect(AttachmentType.AURA), new EnchantedCreatureColorCondition(ObjectColor.BLUE));
        effect.setText("and can't be blocked");
        blueAbility.addEffect(effect);
        this.addAbility(blueAbility);
    }

    private SteelOfTheGodhead(final SteelOfTheGodhead card) {
        super(card);
    }

    @Override
    public SteelOfTheGodhead copy() {
        return new SteelOfTheGodhead(this);
    }
}

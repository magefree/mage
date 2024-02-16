
package mage.cards.h;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.EnchantedCreatureColorCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.EnchantAbility;
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
public final class HelmOfTheGhastlord extends CardImpl {

    public HelmOfTheGhastlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U/B}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // As long as enchanted creature is blue, it gets +1/+1 and has "Whenever this creature deals damage to an opponent, draw a card."
        SimpleStaticAbility blueAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostEnchantedEffect(1, 1), new EnchantedCreatureColorCondition(ObjectColor.BLUE), "As long as enchanted creature is blue, it gets +1/+1"));
        blueAbility.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(new DealsDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1),false), AttachmentType.AURA), new EnchantedCreatureColorCondition(ObjectColor.BLUE), "and has \"Whenever this creature deals damage to an opponent, draw a card.\""));
        this.addAbility(blueAbility);
        // As long as enchanted creature is black, it gets +1/+1 and has "Whenever this creature deals damage to an opponent, that player discards a card."
        SimpleStaticAbility blackAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostEnchantedEffect(1, 1), new EnchantedCreatureColorCondition(ObjectColor.BLACK), "As long as enchanted creature is black, it gets +1/+1"));
        blackAbility.addEffect(new ConditionalContinuousEffect(new GainAbilityAttachedEffect(new DealsDamageToAPlayerTriggeredAbility(new DiscardTargetEffect(1), false, true), AttachmentType.AURA), new EnchantedCreatureColorCondition(ObjectColor.BLACK), "and has \"Whenever this creature deals damage to an opponent, that player discards a card.\""));
        this.addAbility(blackAbility);
    }

    private HelmOfTheGhastlord(final HelmOfTheGhastlord card) {
        super(card);
    }

    @Override
    public HelmOfTheGhastlord copy() {
        return new HelmOfTheGhastlord(this);
    }
}

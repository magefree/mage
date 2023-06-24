
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class MarkOfFury extends CardImpl {

    public MarkOfFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature has haste.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.AURA)));
        // At the beginning of the end step, return Mark of Fury to its owner's hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ReturnToHandSourceEffect(true), TargetController.ANY, false));
    }

    private MarkOfFury(final MarkOfFury card) {
        super(card);
    }

    @Override
    public MarkOfFury copy() {
        return new MarkOfFury(this);
    }
}

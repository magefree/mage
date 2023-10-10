package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GiantInheritance extends CardImpl {

    public GiantInheritance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +5/+5 and has "Whenever this creature attacks, create a Monster Role token attached to up to one target attacking creature."
        Ability triggeredAbility = new AttacksTriggeredAbility(new CreateRoleAttachedTargetEffect(RoleType.MONSTER))
                .setTriggerPhrase("Whenever this creaure attacks, ");
        triggeredAbility.addTarget(new TargetAttackingCreature(0, 1));
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(5, 5));
        ability.addEffect(new GainAbilityAttachedEffect(
                triggeredAbility, AttachmentType.AURA
        ).setText("and has \"Whenever this creature attacks, create a Monster Role token attached to up to one target attacking creature.\""));
        this.addAbility(ability);

        // When Giant Inheritance is put into a graveyard from the battlefield, return it to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(
                new ReturnToHandSourceEffect()
                        .setText("return it to its owner's hand")
        ));
    }

    private GiantInheritance(final GiantInheritance card) {
        super(card);
    }

    @Override
    public GiantInheritance copy() {
        return new GiantInheritance(this);
    }
}

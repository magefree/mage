package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.combat.GoadAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GhoulishImpetus extends CardImpl {

    public GhoulishImpetus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+1, has deathtouch, and is goaded.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(
            new GainAbilityAttachedEffect(DeathtouchAbility.getInstance(), AttachmentType.AURA)
                .setText("has deathtouch")
                .concatBy(",")
        );
        ability.addEffect(new GoadAttachedEffect().concatBy(","));
        this.addAbility(ability);

        // When enchanted creature dies, return Ghoulish Impetus to the battlefield at the beginning of the next end step.
        this.addAbility(new DiesAttachedTriggeredAbility(
            new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                    new ReturnToBattlefieldUnderYourControlTargetEffect()
                        .setText("return {this} to the battlefield"),
                    TargetController.ANY
                )
            ).setText("return {this} to the battlefield at the beginning of the next end step"),
            "enchanted creature",
            false, true, SetTargetPointer.CARD, true
        ));
    }

    private GhoulishImpetus(final GhoulishImpetus card) {
        super(card);
    }

    @Override
    public GhoulishImpetus copy() {
        return new GhoulishImpetus(this);
    }
}
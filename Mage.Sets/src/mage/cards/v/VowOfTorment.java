package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackControllerAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VowOfTorment extends CardImpl {

    public VowOfTorment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2, has menace, and can't attack you or a planeswalker you control.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilityAttachedEffect(
                new MenaceAbility(), AttachmentType.AURA, Duration.WhileOnBattlefield
        ).setText(", has menace"));
        ability.addEffect(new CantAttackControllerAttachedEffect(AttachmentType.AURA, true)
                .setText(", and can't attack you or planeswalkers you control. " +
                        "<i>(It can't be blocked except by two or more creatures.)</i>"));
        this.addAbility(ability);
    }

    private VowOfTorment(final VowOfTorment card) {
        super(card);
    }

    @Override
    public VowOfTorment copy() {
        return new VowOfTorment(this);
    }
}

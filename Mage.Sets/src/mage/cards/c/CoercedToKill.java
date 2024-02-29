package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessEnchantedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 * @author Cguy7777
 */
public final class CoercedToKill extends CardImpl {

    public CoercedToKill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        this.addAbility(new EnchantAbility(auraTarget));

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect()));

        // Enchanted creature has base power and toughness 1/1, has deathtouch, and is an Assassin in addition to its other types.
        Ability ability = new SimpleStaticAbility(new SetBasePowerToughnessEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(DeathtouchAbility.getInstance(), AttachmentType.AURA)
                .setText(", has deathtouch"));
        ability.addEffect(new AddCardSubtypeAttachedEffect(SubType.ASSASSIN, AttachmentType.AURA)
                .setText(", and is an Assassin in addition to its other types"));
        this.addAbility(ability);
    }

    private CoercedToKill(final CoercedToKill card) {
        super(card);
    }

    @Override
    public CoercedToKill copy() {
        return new CoercedToKill(this);
    }
}

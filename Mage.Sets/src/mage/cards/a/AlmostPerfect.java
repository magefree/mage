package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlmostPerfect extends CardImpl {

    public AlmostPerfect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has base power and toughness 9/10 and has indestructible.
        Ability ability = new SimpleStaticAbility(new SetBasePowerToughnessEnchantedEffect(9, 10));
        ability.addEffect(new GainAbilityAttachedEffect(
                IndestructibleAbility.getInstance(), AttachmentType.AURA
        ).setText("and has indestructible"));
        this.addAbility(ability);
    }

    private AlmostPerfect(final AlmostPerfect card) {
        super(card);
    }

    @Override
    public AlmostPerfect copy() {
        return new AlmostPerfect(this);
    }
}

package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class SpiritMantle extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creatures");

    public SpiritMantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has protection from creatures.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilityAttachedEffect(
                new ProtectionAbility(filter), AttachmentType.AURA
        ).setText("and has protection from creatures"));
        this.addAbility(ability);
    }

    private SpiritMantle(final SpiritMantle card) {
        super(card);
    }

    @Override
    public SpiritMantle copy() {
        return new SpiritMantle(this);
    }
}

package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
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
public final class GauntletsOfLight extends CardImpl {

    public GauntletsOfLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +0/+2 and assigns combat damage equal to its toughness rather than its power.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(0, 2));
        ability.addEffect(new CombatDamageByToughnessAttachedEffect(
                null, "and assigns combat damage equal to its toughness rather than its power"
        ));
        this.addAbility(ability);

        // Enchanted creature has "{2}{W}: Untap this creature."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(new SimpleActivatedAbility(
                new UntapSourceEffect().setText("Untap this creature"), new ManaCostsImpl<>("{2}{W}")
        ), AttachmentType.AURA)));
    }

    private GauntletsOfLight(final GauntletsOfLight card) {
        super(card);
    }

    @Override
    public GauntletsOfLight copy() {
        return new GauntletsOfLight(this);
    }
}

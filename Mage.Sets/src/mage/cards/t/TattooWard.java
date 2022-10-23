package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class TattooWard extends CardImpl {

    public TattooWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+1 and has protection from enchantments. This effect doesn't remove Tattoo Ward.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(
                1, 1, Duration.WhileOnBattlefield
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                new ProtectionAbility(StaticFilters.FILTER_PERMANENT_ENCHANTMENTS),
                AttachmentType.AURA, Duration.WhileOnBattlefield
        ).setDoesntRemoveItself(true));
        this.addAbility(ability);

        // Sacrifice Tattoo Ward: Destroy target enchantment.
        ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ENCHANTMENT));
        this.addAbility(ability);

    }

    private TattooWard(final TattooWard card) {
        super(card);
    }

    @Override
    public TattooWard copy() {
        return new TattooWard(this);
    }
}

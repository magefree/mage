
package mage.cards.t;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;

/**
 *
 * @author jeffwadsworth
 */
public final class TattooWard extends CardImpl {

    private static final FilterCard filter = new FilterCard("enchantments");

    static {
        filter.add(CardType.ENCHANTMENT.getPredicate());
    }

    public TattooWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has protection from enchantments. This effect doesn't remove Tattoo Ward.
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield));
        ProtectionAbility protectionAbility = new ProtectionAbility(filter);
        protectionAbility.setAuraIdNotToBeRemoved(getId());
        ability2.addEffect(new GainAbilityAttachedEffect(protectionAbility, AttachmentType.AURA, Duration.WhileOnBattlefield));
        this.addAbility(ability2);

        // Sacrifice Tattoo Ward: Destroy target enchantment.
        Ability ability3 = new SimpleActivatedAbility(new DestroyTargetEffect(), new SacrificeSourceCost());
        ability3.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ENCHANTMENT));
        this.addAbility(ability3);

    }

    private TattooWard(final TattooWard card) {
        super(card);
    }

    @Override
    public TattooWard copy() {
        return new TattooWard(this);
    }
}

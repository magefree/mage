
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureIfVehicleEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public final class SiegeModification extends CardImpl {

    public SiegeModification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature or Vehicle
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_VEHICLE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // As long as enchanted permanent is a Vehicle, it's a creature in addition to its other types.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureIfVehicleEffect()));

        // Enchanted creature gets +3/+0 and has first strike.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(3, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                FirstStrikeAbility.getInstance(), AttachmentType.AURA
        ).setText(" and has first strike"));
        this.addAbility(ability);
    }

    private SiegeModification(final SiegeModification card) {
        super(card);
    }

    @Override
    public SiegeModification copy() {
        return new SiegeModification(this);
    }


}

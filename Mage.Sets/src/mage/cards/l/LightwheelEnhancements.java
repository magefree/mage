package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.MaxSpeedAbility;
import mage.abilities.common.MayCastFromGraveyardSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.StartYourEnginesAbility;
import mage.abilities.keyword.VigilanceAbility;
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
 * @author TheElk801
 */
public final class LightwheelEnhancements extends CardImpl {

    public LightwheelEnhancements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature or Vehicle
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_VEHICLE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Start your engines!
        this.addAbility(new StartYourEnginesAbility());

        // Enchanted permanent gets +1/+1 and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1)
                .setText("enchanted permanent gets +1/+1"));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.AURA
        ).setText("and has vigilance"));
        this.addAbility(ability);

        // Max speed -- You may cast this card from your graveyard.
        this.addAbility(new MaxSpeedAbility(new MayCastFromGraveyardSourceAbility()));
    }

    private LightwheelEnhancements(final LightwheelEnhancements card) {
        super(card);
    }

    @Override
    public LightwheelEnhancements copy() {
        return new LightwheelEnhancements(this);
    }
}

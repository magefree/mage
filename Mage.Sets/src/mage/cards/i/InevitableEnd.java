package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class InevitableEnd extends CardImpl {

    public InevitableEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT},
                "{2}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(
                new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has "At the beginning of your upkeep, sacrifice a
        // creature."
        BeginningOfUpkeepTriggeredAbility triggeredAbility
                = new BeginningOfUpkeepTriggeredAbility(
                        new SacrificeControllerEffect(
                                StaticFilters.FILTER_PERMANENT_CREATURE, 1, null),
                        TargetController.YOU, false);
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                triggeredAbility, AttachmentType.AURA, Duration.WhileOnBattlefield,
                "Enchanted creature has \"" + triggeredAbility.getRule() + "\"")));
    }

    private InevitableEnd(final InevitableEnd card) {
        super(card);
    }

    @Override
    public InevitableEnd copy() {
        return new InevitableEnd(this);
    }
}

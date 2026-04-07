package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.GainFlashbackTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LostInMemories extends CardImpl {

    public LostInMemories(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+1 and has "Whenever this creature deals combat damage to a player, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost."
        TriggeredAbility triggeredAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new GainFlashbackTargetEffect());
        triggeredAbility.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY));
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(triggeredAbility, AttachmentType.AURA)
                .setText("and has \"Whenever this creature deals combat damage to a player, " +
                        "target instant or sorcery card in your graveyard gains flashback until end of turn. " +
                        "The flashback cost is equal to its mana cost.\""));
        this.addAbility(ability);
    }

    private LostInMemories(final LostInMemories card) {
        super(card);
    }

    @Override
    public LostInMemories copy() {
        return new LostInMemories(this);
    }
}

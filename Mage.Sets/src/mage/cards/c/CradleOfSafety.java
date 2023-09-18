package mage.cards.c;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.constants.*;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 *
 * @author weirddan455
 */
public final class CradleOfSafety extends CardImpl {

    public CradleOfSafety(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Cradle of Safety enters the battlefield, enchanted creature gains hexproof until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainAbilityAttachedEffect(
                HexproofAbility.getInstance(), AttachmentType.AURA, Duration.EndOfTurn
        )));

        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(1, 1)));
    }

    private CradleOfSafety(final CradleOfSafety card) {
        super(card);
    }

    @Override
    public CradleOfSafety copy() {
        return new CradleOfSafety(this);
    }
}

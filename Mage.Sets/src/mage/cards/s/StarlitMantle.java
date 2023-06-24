package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarlitMantle extends CardImpl {

    public StarlitMantle(UUID ownerId, CardSetInfo setInfo) {
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

        // When Starlit Mantle enters the battlefield, enchanted creature gains hexproof until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainAbilityAttachedEffect(
                HexproofAbility.getInstance(), AttachmentType.AURA, Duration.EndOfTurn
        )));

        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(1, 1)));
    }

    private StarlitMantle(final StarlitMantle card) {
        super(card);
    }

    @Override
    public StarlitMantle copy() {
        return new StarlitMantle(this);
    }
}

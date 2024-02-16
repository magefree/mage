
package mage.cards.m;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class MantleOfLeadership extends CardImpl {

    public MantleOfLeadership(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever a creature enters the battlefield, enchanted creature gets +2/+2 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new BoostEnchantedEffect(2, 2, Duration.EndOfTurn),
                StaticFilters.FILTER_PERMANENT_A_CREATURE
        ));
    }

    private MantleOfLeadership(final MantleOfLeadership card) {
        super(card);
    }

    @Override
    public MantleOfLeadership copy() {
        return new MantleOfLeadership(this);
    }
}

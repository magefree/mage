
package mage.cards.a;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AetherMeltdown extends CardImpl {

    public AetherMeltdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature or vehicle.
        TargetPermanent auraTarget = new TargetPermanent(StaticFilters.FILTER_PERMANENT_CREATURE_OR_VEHICLE);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Aether Meltdown enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // Enchanted permanent gets -4/-0.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(-4, 0).setText("enchanted permanent gets -4/-0")));
    }

    private AetherMeltdown(final AetherMeltdown card) {
        super(card);
    }

    @Override
    public AetherMeltdown copy() {
        return new AetherMeltdown(this);
    }
}

package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterEnchantmentPermanent;

/**
 *
 * @author Plopman
 */
public final class AuraFlux extends CardImpl {

    public AuraFlux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // Other enchantments have "At the beginning of your upkeep, sacrifice this enchantment unless you pay {2}."
        Ability gainedAbility = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new GenericManaCost(2))
                .setText("sacrifice this enchantment unless you pay {2}"), TargetController.YOU, false);
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(gainedAbility, Duration.WhileOnBattlefield, new FilterEnchantmentPermanent("enchantments"), true)));
    }

    private AuraFlux(final AuraFlux card) {
        super(card);
    }

    @Override
    public AuraFlux copy() {
        return new AuraFlux(this);
    }
}

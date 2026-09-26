package mage.cards.w;

import java.util.UUID;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamageTargetAndTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class WayOfTheWarlord extends CardImpl {

    public WayOfTheWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);

        // When Way of the Warlord enters, empower Jace 5.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(5)));

        // Planeswalkers you control have "[−4]: This planeswalker deals 2 damage to up to one target creature or planeswalker and 2 damage to target player."
        Ability gainedAbility = new LoyaltyAbility(new DamageTargetAndTargetEffect(2, 2), -4);
        gainedAbility.addTarget(new TargetCreatureOrPlaneswalker(0, 1).setTargetTag(1));
        gainedAbility.addTarget(new TargetPlayer().setTargetTag(2));
        this.addAbility(
            new SimpleStaticAbility(new GainAbilityControlledEffect(
                gainedAbility, 
                Duration.WhileOnBattlefield, 
                StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
            ).setText("Planeswalkers you control have \"-4: This planeswalker deals 2 damage to up to one target creature or planeswalker and 2 damage to target player.\""))
        );
    }

    private WayOfTheWarlord(final WayOfTheWarlord card) {
        super(card);
    }

    @Override
    public WayOfTheWarlord copy() {
        return new WayOfTheWarlord(this);
    }
}

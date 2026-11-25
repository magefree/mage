package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.ruleModifying.ZeroLoyaltyRuleDoesntApplyEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author DarioBarrabinoDev
 */
public final class SanctumLurker extends CardImpl {

    private static final FilterControlledPlaneswalkerPermanent filter
            = new FilterControlledPlaneswalkerPermanent("planeswalkers you control");

    public SanctumLurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters, empower Jace 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(1)));

        // Planeswalkers you control aren't put into their owners' graveyards for having 0 loyalty.
        this.addAbility(new SimpleStaticAbility(new ZeroLoyaltyRuleDoesntApplyEffect(filter)));

        // Planeswalkers you control have "[+2]: This planeswalker deals 1 damage to each opponent and you gain 1 life."
        Ability gainedAbility = new LoyaltyAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT, "this planeswalker"), 2
        );
        gainedAbility.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                gainedAbility, Duration.WhileOnBattlefield, filter
        ).withForceQuotes()));
    }

    private SanctumLurker(final SanctumLurker card) {
        super(card);
    }

    @Override
    public SanctumLurker copy() {
        return new SanctumLurker(this);
    }
}

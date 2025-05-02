package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAnchorWordAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ModeChoice;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author androosss
 */
public final class FrostcliffSiege extends CardImpl {

    public FrostcliffSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{R}");

        // As this enchantment enters, choose Jeskai or Temur.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.JESKAI, ModeChoice.TEMUR)));

        // * Jeskai -- Whenever one or more creatures you control deal combat damage to a player, draw a card.
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(
                new OneOrMoreCombatDamagePlayerTriggeredAbility(new DrawCardSourceControllerEffect(1)), ModeChoice.JESKAI
        )));

        // * Temur -- Creatures you control get +1/+0 and have trample and haste.
        Ability ability = new SimpleStaticAbility(
                new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield)
        );
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and have trample"));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("and haste"));
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(ability, ModeChoice.TEMUR)));
    }

    private FrostcliffSiege(final FrostcliffSiege card) {
        super(card);
    }

    @Override
    public FrostcliffSiege copy() {
        return new FrostcliffSiege(this);
    }

}

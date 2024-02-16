package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CultistOfTheAbsolute extends CardImpl {

    public CultistOfTheAbsolute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own get +3/+3 and have flying, deathtouch, "Ward—Pay 3 life," and "At the beginning of your upkeep, sacrifice a creature."
        Ability ability = new SimpleStaticAbility(new BoostAllEffect(
                3, 3, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER, false
        ));
        ability.addEffect(new GainAbilityAllEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        ).setText("and have flying"));
        ability.addEffect(new GainAbilityAllEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        ).setText(", deathtouch"));
        ability.addEffect(new GainAbilityAllEffect(
                new WardAbility(new PayLifeCost(3)), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        ).setText(", \"Ward&mdash;Pay 3 life,\""));
        ability.addEffect(new GainAbilityAllEffect(
                new BeginningOfUpkeepTriggeredAbility(new SacrificeControllerEffect(
                        StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, 1, null
                ), TargetController.YOU, false),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        ).setText("and \"At the beginning of your upkeep, sacrifice a creature.\""));
        this.addAbility(ability);
    }

    private CultistOfTheAbsolute(final CultistOfTheAbsolute card) {
        super(card);
    }

    @Override
    public CultistOfTheAbsolute copy() {
        return new CultistOfTheAbsolute(this);
    }
}

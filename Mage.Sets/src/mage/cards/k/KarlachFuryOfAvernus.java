package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.condition.common.FirstCombatPhaseCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KarlachFuryOfAvernus extends CardImpl {

    public KarlachFuryOfAvernus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIEFLING);
        this.subtype.add(SubType.BARBARIAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever you attack, if it's the first combat phase of the turn, untap all attacking creatures. They gain first strike until end of turn. After this phase, there is an additional combat phase.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksWithCreaturesTriggeredAbility(
                        new UntapAllEffect(StaticFilters.FILTER_ATTACKING_CREATURES), 1
                ), FirstCombatPhaseCondition.instance, "Whenever you attack, if it's the first " +
                "combat phase of the turn, untap all attacking creatures. They gain first strike " +
                "until end of turn. After this phase, there is an additional combat phase."
        );
        ability.addEffect(new GainAbilityAllEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ));
        ability.addEffect(new AdditionalCombatPhaseEffect());
        this.addAbility(ability);

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private KarlachFuryOfAvernus(final KarlachFuryOfAvernus card) {
        super(card);
    }

    @Override
    public KarlachFuryOfAvernus copy() {
        return new KarlachFuryOfAvernus(this);
    }
}

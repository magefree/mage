package mage.cards.r;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class RootManipulation extends CardImpl {

    public RootManipulation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{G}");

        // Until end of turn, creatures you control get +2/+2 and gain menace and "Whenever this creature attacks, you gain 1 life."
        Ability ability = new AttacksTriggeredAbility(new GainLifeEffect(1));

        this.getSpellAbility().addEffect(new BoostControlledEffect(
            2, 2, Duration.EndOfTurn
        ).setText("Until end of turn, creatures you control get +2/+2"));
        this.getSpellAbility().addEffect(
            new GainAbilityControlledEffect(
                new MenaceAbility(true),
                Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
            ).setText("and gain menace")
        );
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
            ability, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and \"Whenever this creature attacks, you gain 1 life.\""));
    }

    private RootManipulation(final RootManipulation card) {
        super(card);
    }

    @Override
    public RootManipulation copy() {
        return new RootManipulation(this);
    }
}

package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.combat.GoadAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TurnPhase;
import mage.filter.FilterPlayer;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentOrPlayer;

/**
 *
 * @author matoro
 */
public final class TakeTheBait extends CardImpl {

    public TakeTheBait(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{W}");

        // Cast this spell only during an opponent's turn and only during combat.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT, OnOpponentsTurnCondition.instance));

        // Prevent all combat damage that would be dealt to you and planeswalkers you control this turn.
        this.getSpellAbility().addEffect(
            new PreventAllDamageToAllEffect(
                Duration.EndOfTurn,
                new FilterPermanentOrPlayer(
                    "you and planeswalkers you control",
                    StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER,
                    new FilterPlayer()
                ),
                true
            )
        );

        // Untap all attacking creatures
        this.getSpellAbility().addEffect(new UntapAllEffect(StaticFilters.FILTER_ATTACKING_CREATURES));

        // and goad them.
        this.getSpellAbility().addEffect(new GoadAllEffect(StaticFilters.FILTER_ATTACKING_CREATURES));

        // After this phase, there is an additional combat phase.
        this.getSpellAbility().addEffect(new AdditionalCombatPhaseEffect());
    }

    private TakeTheBait(final TakeTheBait card) {
        super(card);
    }

    @Override
    public TakeTheBait copy() {
        return new TakeTheBait(this);
    }
}

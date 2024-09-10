package mage.cards.s;

import mage.abilities.Mode;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.EntwineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TurnPhase;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class SavageBeating extends CardImpl {

    public SavageBeating(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{R}");

        // Cast Savage Beating only during your turn and only during combat.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT, null, MyTurnCondition.instance,
                "Cast this spell only during your turn and only during combat"));

        // Choose one - Creatures you control gain double strike until end of turn;
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES, false));

        // or untap all creatures you control and after this phase, there is an additional combat phase.
        Mode mode = new Mode(new UntapAllControllerEffect(new FilterControlledCreaturePermanent(), "untap all creatures you control"));
        mode.addEffect(new AdditionalCombatPhaseEffect());
        this.getSpellAbility().getModes().addMode(mode);

        // Entwine {1}{R}
        this.addAbility(new EntwineAbility("{1}{R}"));
    }

    private SavageBeating(final SavageBeating card) {
        super(card);
    }

    @Override
    public SavageBeating copy() {
        return new SavageBeating(this);
    }
}

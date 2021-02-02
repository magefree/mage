package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AttackedThisStepCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.watchers.common.PlayerAttackedStepWatcher;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ConfrontTheAssault extends CardImpl {

    public ConfrontTheAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // Cast this spell only if a creature is attacking you.
        Ability ability = new CastOnlyDuringPhaseStepSourceAbility(
                TurnPhase.COMBAT, PhaseStep.DECLARE_ATTACKERS, AttackedThisStepCondition.instance,
                "Cast this spell only if a creature is attacking you."
        );
        ability.addWatcher(new PlayerAttackedStepWatcher());
        this.addAbility(ability);

        // Create three 1/1 white Spirit creature tokens with flying.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SpiritWhiteToken(), 3));
    }

    private ConfrontTheAssault(final ConfrontTheAssault card) {
        super(card);
    }

    @Override
    public ConfrontTheAssault copy() {
        return new ConfrontTheAssault(this);
    }
}
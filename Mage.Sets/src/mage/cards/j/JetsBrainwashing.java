package mage.cards.j;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AddContinuousEffectToGame;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.permanent.token.ClueArtifactToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JetsBrainwashing extends CardImpl {

    public JetsBrainwashing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        // Target creature can't block this turn. If this spell was kicked, gain control of that creature until end of turn, untap it, and it gains haste until end of turn.
        this.getSpellAbility().addEffect(new CantBlockTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new AddContinuousEffectToGame(
                        new GainControlTargetEffect(Duration.EndOfTurn),
                        new GainAbilityTargetEffect(HasteAbility.getInstance())
                ), KickedCondition.ONCE, "If this spell was kicked, gain control of that creature " +
                "until end of turn, untap it, and it gains haste until end of turn"
        ).addEffect(new UntapTargetEffect()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Create a Clue token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ClueArtifactToken()).concatBy("<br>"));
    }

    private JetsBrainwashing(final JetsBrainwashing card) {
        super(card);
    }

    @Override
    public JetsBrainwashing copy() {
        return new JetsBrainwashing(this);
    }
}

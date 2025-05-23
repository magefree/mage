package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.RedWarriorToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SaltRoadSkirmish extends CardImpl {

    public SaltRoadSkirmish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Destroy target creature. Create two 1/1 red Warrior creature tokens. They gain haste until end of turn. Sacrifice them at the beginning of the next end step.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new SaltRoadSkirmishEffect());
    }

    private SaltRoadSkirmish(final SaltRoadSkirmish card) {
        super(card);
    }

    @Override
    public SaltRoadSkirmish copy() {
        return new SaltRoadSkirmish(this);
    }
}

class SaltRoadSkirmishEffect extends OneShotEffect {

    SaltRoadSkirmishEffect() {
        super(Outcome.Benefit);
        staticText = "create two 1/1 red Warrior creature tokens. " +
                "They gain haste until end of turn. Sacrifice them at the beginning of the next end step";
    }

    private SaltRoadSkirmishEffect(final SaltRoadSkirmishEffect effect) {
        super(effect);
    }

    @Override
    public SaltRoadSkirmishEffect copy() {
        return new SaltRoadSkirmishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new RedWarriorToken();
        token.putOntoBattlefield(2, game, source);
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTargets(token, game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect("sacrifice them").setTargetPointer(new FixedTargets(token, game))
        ), source);
        return true;
    }
}

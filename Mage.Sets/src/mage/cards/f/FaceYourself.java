package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author muz
 */
public final class FaceYourself extends CardImpl {

    public FaceYourself(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // For each creature target player controls, create a token that's a copy of that creature, except it has haste and "At the beginning of the end step, if you don't control a planeswalker, sacrifice this creature."
        this.getSpellAbility().addEffect(new FaceYourselfEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private FaceYourself(final FaceYourself card) {
        super(card);
    }

    @Override
    public FaceYourself copy() {
        return new FaceYourself(this);
    }
}

class FaceYourselfEffect extends OneShotEffect {

    FaceYourselfEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each creature target player controls, create a token that's a copy "
            + "of that creature, except it has haste and \"At the beginning of the end step, if "
            + "you don't control a planeswalker, sacrifice this creature.\"";
    }

    private FaceYourselfEffect(final FaceYourselfEffect effect) {
        super(effect);
    }

    @Override
    public FaceYourselfEffect copy() {
        return new FaceYourselfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, targetPlayer.getId(), game)) {
                if (permanent != null) {
                    CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect().addAdditionalAbilities(
                        HasteAbility.getInstance(),
                        new BeginningOfEndStepTriggeredAbility(
                            TargetController.NEXT,
                            new SacrificeSourceEffect(),
                            false,
                            new InvertCondition(
                                new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER),
                                "you don't control a planeswalker"
                            )
                        )
                    );
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}

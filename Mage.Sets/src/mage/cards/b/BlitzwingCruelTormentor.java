package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlitzwingCruelTormentor extends CardImpl {

    public BlitzwingCruelTormentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.b.BlitzwingAdaptiveAssailant.class;

        // More Than Meets the Eye {3}{B}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{3}{B}"));

        // At the beginning of your end step, target opponent loses life equal to the life that player lost this turn. If no life is lost this way, convert Blitzwing.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new BlitzwingCruelTormentorEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BlitzwingCruelTormentor(final BlitzwingCruelTormentor card) {
        super(card);
    }

    @Override
    public BlitzwingCruelTormentor copy() {
        return new BlitzwingCruelTormentor(this);
    }
}

class BlitzwingCruelTormentorEffect extends OneShotEffect {

    BlitzwingCruelTormentorEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent loses life equal to the life that player " +
                "lost this turn. If no life is lost this way, convert {this}";
    }

    private BlitzwingCruelTormentorEffect(final BlitzwingCruelTormentorEffect effect) {
        super(effect);
    }

    @Override
    public BlitzwingCruelTormentorEffect copy() {
        return new BlitzwingCruelTormentorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            int lifeLost = game.getState().getWatcher(PlayerLostLifeWatcher.class).getLifeLost(player.getId());
            if (lifeLost > 0 && player.loseLife(lifeLost, game, source, false) > 0) {
                return true;
            }
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.transform(source, game);
    }
}

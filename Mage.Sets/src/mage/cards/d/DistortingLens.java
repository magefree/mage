package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author anonymous
 */
public final class DistortingLens extends CardImpl {

    public DistortingLens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {tap}: Target permanent becomes the color of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ChangeColorEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private DistortingLens(final DistortingLens card) {
        super(card);
    }

    @Override
    public DistortingLens copy() {
        return new DistortingLens(this);
    }
}

class ChangeColorEffect extends OneShotEffect {

    public ChangeColorEffect() {
        super(Outcome.Neutral);
        staticText = "Target permanent becomes the color of your choice until end of turn";
    }

    public ChangeColorEffect(final ChangeColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        Permanent chosen = game.getPermanent(targetPointer.getFirst(game, source));
        if (player != null && permanent != null) {
            ContinuousEffect effect = new BecomesColorTargetEffect(null, Duration.EndOfTurn);
            effect.setTargetPointer(new FixedTarget(chosen.getId(), game));
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    @Override
    public ChangeColorEffect copy() {
        return new ChangeColorEffect(this);
    }
}

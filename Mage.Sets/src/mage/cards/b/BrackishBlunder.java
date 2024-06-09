package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.MapToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrackishBlunder extends CardImpl {

    public BrackishBlunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target creature to its owner's hand. If it was tapped, create a Map token.
        this.getSpellAbility().addEffect(new BrackishBlunderEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BrackishBlunder(final BrackishBlunder card) {
        super(card);
    }

    @Override
    public BrackishBlunder copy() {
        return new BrackishBlunder(this);
    }
}

class BrackishBlunderEffect extends OneShotEffect {

    BrackishBlunderEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature to its owner's hand. If it was tapped, create a Map token";
    }

    private BrackishBlunderEffect(final BrackishBlunderEffect effect) {
        super(effect);
    }

    @Override
    public BrackishBlunderEffect copy() {
        return new BrackishBlunderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        boolean tapped = permanent.isTapped();
        player.moveCards(permanent, Zone.HAND, source, game);
        if (tapped) {
            new MapToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}

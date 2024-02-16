package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class WhiskAway extends CardImpl {

    private static final FilterAttackingOrBlockingCreature filter = new FilterAttackingOrBlockingCreature();

    public WhiskAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Put target attacking or blocking creature on top of its owner's library.
        this.getSpellAbility().addEffect(new WhiskAwayEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private WhiskAway(final WhiskAway card) {
        super(card);
    }

    @Override
    public WhiskAway copy() {
        return new WhiskAway(this);
    }
}

class WhiskAwayEffect extends OneShotEffect {

    WhiskAwayEffect() {
        super(Outcome.Removal);
        staticText = "Put target attacking or blocking creature on top of its owner's library";
    }

    private WhiskAwayEffect(final WhiskAwayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (targetCreature != null && controller != null) {
            return controller.putCardsOnTopOfLibrary(targetCreature, game, source, true);
        }
        return false;
    }

    @Override
    public WhiskAwayEffect copy() {
        return new WhiskAwayEffect(this);
    }
}

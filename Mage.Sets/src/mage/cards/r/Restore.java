
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class Restore extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard();

    public Restore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Put target land card from a graveyard onto the battlefield under your control.
        this.getSpellAbility().addEffect(new RestoreEffect());
        Target target = new TargetCardInGraveyard(filter);
        this.getSpellAbility().addTarget(target);
    }

    private Restore(final Restore card) {
        super(card);
    }

    @Override
    public Restore copy() {
        return new Restore(this);
    }
}

class RestoreEffect extends OneShotEffect {

    public RestoreEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "Put target land card from a graveyard onto the battlefield under your control";
    }

    private RestoreEffect(final RestoreEffect effect) {
        super(effect);
    }

    @Override
    public RestoreEffect copy() {
        return new RestoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card land = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (controller != null && game.getState().getZone(land.getId()) == Zone.GRAVEYARD) {
            controller.moveCards(land, Zone.BATTLEFIELD, source, game);
            return true;
        }
        return false;
    }
}

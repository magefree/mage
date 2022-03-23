
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class TwistedJustice extends CardImpl {

    public TwistedJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{B}");

        // Target player sacrifices a creature. You draw cards equal to that creature's power.
        this.getSpellAbility().addEffect(new TwistedJusticeEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private TwistedJustice(final TwistedJustice card) {
        super(card);
    }

    @Override
    public TwistedJustice copy() {
        return new TwistedJustice(this);
    }
}

class TwistedJusticeEffect extends OneShotEffect {

    TwistedJusticeEffect() {
        super(Outcome.Sacrifice);
        staticText = "Target player sacrifices a creature. You draw cards equal to that creature's power";
    }

    TwistedJusticeEffect(TwistedJusticeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());

        FilterControlledPermanent filter = new FilterControlledPermanent("creature");
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
        TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);

        //A spell or ability could have removed the only legal target this player
        //had, if thats the case this ability should fizzle.
        if (target.canChoose(player.getId(), source, game)) {
            player.choose(Outcome.Sacrifice, target, source, game);

            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                permanent.sacrifice(source, game);
                controller.drawCards(permanent.getPower().getValue(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public TwistedJusticeEffect copy() {
        return new TwistedJusticeEffect(this);
    }

}

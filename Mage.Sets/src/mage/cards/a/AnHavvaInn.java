
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class AnHavvaInn extends CardImpl {

    public AnHavvaInn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}{G}");

        // You gain X plus 1 life, where X is the number of green creatures on the battlefield.
        this.getSpellAbility().addEffect(new AnHavvaInnEffect());
    }

    private AnHavvaInn(final AnHavvaInn card) {
        super(card);
    }

    @Override
    public AnHavvaInn copy() {
        return new AnHavvaInn(this);
    }
}

class AnHavvaInnEffect extends OneShotEffect {

    public AnHavvaInnEffect() {
        super(Outcome.GainLife);
        staticText = "You gain X plus 1 life, where X is the number of green creatures on the battlefield";
    }

    public AnHavvaInnEffect(final AnHavvaInnEffect effect) {
        super(effect);
    }

    @Override
    public AnHavvaInnEffect copy() {
        return new AnHavvaInnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("green creatures");
            filter.add(new ColorPredicate(ObjectColor.GREEN));
            int greenCreatures = game.getBattlefield().count(filter, source.getControllerId(), source, game);
            player.gainLife(greenCreatures+1, game, source);
        }
        return true;
    }

}

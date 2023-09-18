
package mage.cards.l;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class LilianasDefeat extends CardImpl {

    public LilianasDefeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        FilterPermanent filter = new FilterCreatureOrPlaneswalkerPermanent("black creature or black planeswalker");
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        // Destroy target black creature or black planeswalker. If that permanent was a Liliana planeswalker, her controller loses 3 life.
        this.getSpellAbility().addEffect(new LilianasDefeatEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private LilianasDefeat(final LilianasDefeat card) {
        super(card);
    }

    @Override
    public LilianasDefeat copy() {
        return new LilianasDefeat(this);
    }
}

class LilianasDefeatEffect extends OneShotEffect {

    public LilianasDefeatEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target black creature or black planeswalker. If that permanent was a Liliana planeswalker, her controller loses 3 life";
    }

    private LilianasDefeatEffect(final LilianasDefeatEffect effect) {
        super(effect);
    }

    @Override
    public LilianasDefeatEffect copy() {
        return new LilianasDefeatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player != null && permanent != null) {
            permanent.destroy(source, game, true);
            game.getState().processAction(game);
            if (permanent.isPlaneswalker(game) && permanent.hasSubtype(SubType.LILIANA, game)) {
                Player permanentController = game.getPlayer(permanent.getControllerId());
                if (permanentController != null) {
                    permanentController.loseLife(3, game, source, false);
                }
            }
            return true;
        }
        return false;
    }
}

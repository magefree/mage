
package mage.cards.h;

import java.util.Iterator;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class HomewardPath extends CardImpl {

    public HomewardPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Each player gains control of all creatures they own.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new HomewardPathControlEffect(), new TapSourceCost()));

    }

    private HomewardPath(final HomewardPath card) {
        super(card);
    }

    @Override
    public HomewardPath copy() {
        return new HomewardPath(this);
    }
}

class HomewardPathControlEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public HomewardPathControlEffect() {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.staticText = "Each player gains control of all creatures they own";
    }

    public HomewardPathControlEffect(final HomewardPathControlEffect effect) {
        super(effect);
    }

    @Override
    public HomewardPathControlEffect copy() {
        return new HomewardPathControlEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        // add all creatures in range
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                FilterPermanent playerFilter = filter.copy();
                playerFilter.add(new OwnerIdPredicate(playerId));
                for (Permanent permanent :game.getBattlefield().getActivePermanents(playerFilter, playerId, game)) {
                    affectedObjectList.add(new MageObjectReference(permanent, game));
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { 
            Permanent creature = it.next().getPermanent(game);
            if (creature != null) {
                if (!creature.isControlledBy(creature.getOwnerId())) {
                    creature.changeControllerId(creature.getOwnerId(), game, source);
                }
            } else {
                it.remove();
            }
        }
        if (affectedObjectList.isEmpty()) {
            this.discard();
        }
        return true;
    }
}


package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class RoilingTerrain extends CardImpl {

    public RoilingTerrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}{R}");

        // Destroy target land, then Roiling Terrain deals damage to that land's controller equal to the number of land cards in that player's graveyard.
        this.getSpellAbility().addEffect(new RoilingTerrainEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    public RoilingTerrain(final RoilingTerrain card) {
        super(card);
    }

    @Override
    public RoilingTerrain copy() {
        return new RoilingTerrain(this);
    }
}

class RoilingTerrainEffect extends OneShotEffect {

    public RoilingTerrainEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Destroy target land, then {this} deals damage to that land's controller equal to the number of land cards in that player's graveyard";
    }

    public RoilingTerrainEffect(final RoilingTerrainEffect effect) {
        super(effect);
    }

    @Override
    public RoilingTerrainEffect copy() {
        return new RoilingTerrainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetedLand = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (targetedLand != null) {
            targetedLand.destroy(source.getSourceId(), game, true);
            Player targetController = game.getPlayer(targetedLand.getControllerId());
            if (targetController != null) {
                int landsInGraveyard = targetController.getGraveyard().count(new FilterLandCard(), game);
                targetController.damage(landsInGraveyard, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}

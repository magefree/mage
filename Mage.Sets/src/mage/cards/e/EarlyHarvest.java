
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class EarlyHarvest extends CardImpl {

    public EarlyHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}{G}");

        // Target player untaps all basic lands they control.
        this.getSpellAbility().addEffect(new UntapAllLandsTargetEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private EarlyHarvest(final EarlyHarvest card) {
        super(card);
    }

    @Override
    public EarlyHarvest copy() {
        return new EarlyHarvest(this);
    }
}

class UntapAllLandsTargetEffect extends OneShotEffect {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent();
    static {
        filter.add(SuperType.BASIC.getPredicate());
    }
    
    public UntapAllLandsTargetEffect() {
        super(Outcome.Untap);
        staticText = "Target player untaps all basic lands they control";
    }

    private UntapAllLandsTargetEffect(final UntapAllLandsTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            for (Permanent land: game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                land.untap(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public UntapAllLandsTargetEffect copy() {
        return new UntapAllLandsTargetEffect(this);
    }

}

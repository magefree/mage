

package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetLandPermanent;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 *
 * @author LevelX
 */
public final class FeastOfWorms extends CardImpl {

    public FeastOfWorms (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");
        this.subtype.add(SubType.ARCANE);


        // Destroy target land. If that land was legendary, its controller sacrifices another land.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new FeastOfWormsEffect());
    }

    private FeastOfWorms(final FeastOfWorms card) {
        super(card);
    }

    @Override
    public FeastOfWorms copy() {
        return new FeastOfWorms(this);
    }

}

class FeastOfWormsEffect extends OneShotEffect {

    FeastOfWormsEffect() {
        super(Outcome.Sacrifice);
        staticText = "If that land was legendary, its controller sacrifices another land";
    }

    private FeastOfWormsEffect(final FeastOfWormsEffect effect) {
        super(effect);
    }

    @Override
    public FeastOfWormsEffect copy() {
        return new FeastOfWormsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(id);
        Player targetPlayer = null;
        if (permanent != null) {
            targetPlayer = game.getPlayer(permanent.getControllerId());
        }
        if (targetPlayer != null && permanent.isLegendary(game)) {
            TargetSacrifice target = new TargetSacrifice(StaticFilters.FILTER_LAND);
            if (target.canChoose(targetPlayer.getId(), source, game)) {
                targetPlayer.choose(Outcome.Sacrifice, target, source, game);
                Permanent land = game.getPermanent(target.getFirstTarget());
                if (land != null) {
                    land.sacrifice(source, game);
                }
            }
            return true;            
        }
        return false;
    }
}

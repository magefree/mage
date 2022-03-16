
package mage.cards.r;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class RealitySpasm extends CardImpl {
    
    public RealitySpasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}{U}");


        // Choose one - Tap X target permanents; or untap X target permanents.
        this.getSpellAbility().addEffect(new RealitySpasmTapEffect());
        Mode mode = new Mode(new RealitySpasmUntapEffect());
        this.getSpellAbility().addMode(mode);
    }

    private RealitySpasm(final RealitySpasm card) {
        super(card);
    }

    @Override
    public RealitySpasm copy() {
        return new RealitySpasm(this);
    }
}

class RealitySpasmTapEffect extends OneShotEffect {
    
    private static final FilterPermanent filter = new FilterPermanent("permanent");

    public RealitySpasmTapEffect() {
        super(Outcome.Tap);
        staticText = "Tap X target permanents";
    }

    public RealitySpasmTapEffect(final RealitySpasmTapEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numberToTap = source.getManaCostsToPay().getX();
        numberToTap = Math.min(game.getBattlefield().getAllActivePermanents().size(), numberToTap);
        TargetPermanent target = new TargetPermanent(numberToTap, filter);
        if (target.canChoose(source.getSourceId(), source.getControllerId(), game) && target.choose(Outcome.Tap, source.getControllerId(), source.getSourceId(), game)) {
            if (!target.getTargets().isEmpty()) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.tap(source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public RealitySpasmTapEffect copy() {
        return new RealitySpasmTapEffect(this);
    }

}

class RealitySpasmUntapEffect extends OneShotEffect {
    
    private static final FilterPermanent filter = new FilterPermanent("permanent");

    public RealitySpasmUntapEffect() {
        super(Outcome.Untap);
        staticText = "Untap X target permanents";
    }

    public RealitySpasmUntapEffect(final RealitySpasmUntapEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int numberToTap = source.getManaCostsToPay().getX();
        numberToTap = Math.min(game.getBattlefield().getAllActivePermanents().size(), numberToTap);
        TargetPermanent target = new TargetPermanent(numberToTap, filter);
        if (target.canChoose(source.getSourceId(), source.getControllerId(), game) && target.choose(Outcome.Untap, source.getControllerId(), source.getSourceId(), game)) {
            if (!target.getTargets().isEmpty()) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        permanent.untap(game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public RealitySpasmUntapEffect copy() {
        return new RealitySpasmUntapEffect(this);
    }

}

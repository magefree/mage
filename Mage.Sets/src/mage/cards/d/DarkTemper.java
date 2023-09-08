
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class DarkTemper extends CardImpl {

    public DarkTemper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");


        // Dark Temper deals 2 damage to target creature. If you control a black permanent, destroy the creature instead.
        this.getSpellAbility().addEffect(new DarkTemperEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DarkTemper(final DarkTemper card) {
        super(card);
    }

    @Override
    public DarkTemper copy() {
        return new DarkTemper(this);
    }
}

class DarkTemperEffect extends OneShotEffect {

    public DarkTemperEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to target creature. If you control a black permanent, destroy the creature instead";
    }

    private DarkTemperEffect(final DarkTemperEffect effect) {
        super(effect);
    }

    @Override
    public DarkTemperEffect copy() {
        return new DarkTemperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent == null) {
            return false;
        }

        FilterPermanent filter = new FilterPermanent("black permanent");
        filter.add(new ColorPredicate(ObjectColor.BLACK));

        if (game.getBattlefield().countAll(filter, source.getControllerId(), game) == 0) {
            permanent.damage(2, source.getSourceId(), source, game, false, true);
        } else {
            permanent.destroy(source, game, false);
        }
        return true;
    }
}

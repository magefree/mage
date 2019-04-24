
package mage.cards.b;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class BileBlight extends CardImpl {

    public BileBlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{B}");


        // Target creature and all creatures with the same name as that creature get -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BileBlightEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public BileBlight(final BileBlight card) {
        super(card);
    }

    @Override
    public BileBlight copy() {
        return new BileBlight(this);
    }
}

class BileBlightEffect extends BoostAllEffect {

    public BileBlightEffect() {
        super(-3, -3, Duration.EndOfTurn);
        staticText = "Target creature and all creatures with the same name as that creature get -3/-3 until end of turn";
    }

    public BileBlightEffect(final BileBlightEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.clear();
        if (this.affectedObjectsSet) {
            Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (target != null) {
                if (target.getName().isEmpty()) { // face down creature
                    affectedObjectList.add(new MageObjectReference(target, game));
                } else {
                    String name = target.getName();
                    for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                        if (perm.getName().equals(name)) {
                            affectedObjectList.add(new MageObjectReference(perm, game));
                        }
                    }
                }
            }
        }
    }

    @Override
    public BileBlightEffect copy() {
        return new BileBlightEffect(this);
    }
}

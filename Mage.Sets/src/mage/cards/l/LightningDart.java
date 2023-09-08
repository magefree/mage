
package mage.cards.l;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Derpthemeus
 */
public final class LightningDart extends CardImpl {

    public LightningDart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");

        // Lightning Dart deals 1 damage to target creature. If that creature is white or blue, Lightning Dart deals 4 damage to it instead.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new LightningDartEffect());
    }

    private LightningDart(final LightningDart card) {
        super(card);
    }

    @Override
    public LightningDart copy() {
        return new LightningDart(this);
    }

    static class LightningDartEffect extends OneShotEffect {

        public LightningDartEffect() {
            super(Outcome.Damage);
            this.staticText = "Lightning Dart deals 1 damage to target creature. If that creature is white or blue, Lightning Dart deals 4 damage to it instead";
        }

        private LightningDartEffect(final LightningDartEffect effect) {
            super(effect);
        }

        @Override
        public LightningDartEffect copy() {
            return new LightningDartEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null) {
                int damage = 1;
                ObjectColor color = permanent.getColor(game);
                if (color.isWhite() || color.isBlue()) {
                    damage = 4;
                }
                permanent.damage(damage, source.getId(), source, game, false, false);
            }

            return false;
        }
    }
}

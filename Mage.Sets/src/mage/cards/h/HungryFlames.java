package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author JRHerlehy
 */
public final class HungryFlames extends CardImpl {

    public HungryFlames(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Hungry Flames deals 3 damage to target creature and 2 damage to target player.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        this.getSpellAbility().addEffect(new HungryFlamesEffect());
    }

    private HungryFlames(final HungryFlames card) {
        super(card);
    }

    @Override
    public HungryFlames copy() {
        return new HungryFlames(this);
    }

    private static class HungryFlamesEffect extends OneShotEffect {

        HungryFlamesEffect() {
            super(Outcome.Damage);
            this.staticText = "{this} deals 3 damage to target creature and 2 damage to target player or planeswalker";
        }

        private HungryFlamesEffect(final HungryFlamesEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
            Player player = game.getPlayer(source.getTargets().get(1).getFirstTarget());

            if (permanent != null) {
                permanent.damage(3, source.getSourceId(), source, game, false, true);
            }

            if (player != null) {
                player.damage(2, source.getSourceId(), source, game);
            }

            return true;
        }

        @Override
        public HungryFlamesEffect copy() {
            return new HungryFlamesEffect(this);
        }
    }

}

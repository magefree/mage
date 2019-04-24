
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public final class RakdosCharm extends CardImpl {

    public RakdosCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{R}");

        // Choose one — Exile all cards from target player's graveyard;
        this.getSpellAbility().addEffect(new ExileGraveyardAllTargetPlayerEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

        // or destroy target artifact;
        Mode mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        mode.getTargets().add(new TargetArtifactPermanent());
        this.getSpellAbility().addMode(mode);

        // or each creature deals 1 damage to its controller.
        mode = new Mode();
        mode.getEffects().add(new RakdosCharmDamageEffect());
        this.getSpellAbility().addMode(mode);
    }

    public RakdosCharm(final RakdosCharm card) {
        super(card);
    }

    @Override
    public RakdosCharm copy() {
        return new RakdosCharm(this);
    }

    private static class RakdosCharmDamageEffect extends OneShotEffect {

    public RakdosCharmDamageEffect() {
            super(Outcome.Detriment);
            staticText = "each creature deals 1 damage to its controller";
        }

        public RakdosCharmDamageEffect(final RakdosCharmDamageEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {

            FilterPermanent filter = new FilterPermanent();
            filter.add(new CardTypePredicate(CardType.CREATURE));

            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
                Player controller = game.getPlayer(permanent.getControllerId());
                if (controller != null) {
                    controller.damage(1, permanent.getId(), game, false, true);
                    game.informPlayers("1 damage to " + controller.getLogName() + " from " + permanent.getName());
                }
            }
            return true;
        }

        @Override
        public RakdosCharmDamageEffect copy() {
            return new RakdosCharmDamageEffect(this);
        }
    }
}
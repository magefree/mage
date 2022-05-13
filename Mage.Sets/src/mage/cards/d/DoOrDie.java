
package mage.cards.d;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class DoOrDie extends CardImpl {

    public DoOrDie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}");

        // Separate all creatures target player controls into two piles. Destroy all creatures in the pile of that player's choice. They can't be regenerated.
        this.getSpellAbility().addEffect(new DoOrDieEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private DoOrDie(final DoOrDie card) {
        super(card);
    }

    @Override
    public DoOrDie copy() {
        return new DoOrDie(this);
    }
}

class DoOrDieEffect extends OneShotEffect {

    public DoOrDieEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Separate all creatures target player controls into two piles. Destroy all creatures in the pile of that player's choice. They can't be regenerated";
    }

    public DoOrDieEffect(final DoOrDieEffect effect) {
        super(effect);
    }

    @Override
    public DoOrDieEffect copy() {
        return new DoOrDieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (player != null && targetPlayer != null) {
            int count = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURES, targetPlayer.getId(), game);
            TargetCreaturePermanent creatures = new TargetCreaturePermanent(0, count, new FilterCreaturePermanent("creatures to put in the first pile"), true);
            List<Permanent> pile1 = new ArrayList<>();
            creatures.setRequired(false);
            if (player.choose(Outcome.Neutral, creatures, source, game)) {
                List<UUID> targets = creatures.getTargets();
                for (UUID targetId : targets) {
                    Permanent p = game.getPermanent(targetId);
                    if (p != null) {
                        pile1.add(p);
                    }
                }
            }
            List<Permanent> pile2 = new ArrayList<>();
            for (Permanent p : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, targetPlayer.getId(), game)) {
                if (!pile1.contains(p)) {
                    pile2.add(p);
                }
            }

            boolean choice = targetPlayer.choosePile(Outcome.DestroyPermanent, "Choose a pile to destroy.", pile1, pile2, game);

            if (choice) {
                destroyPermanents(pile1, game, source);
            } else {
                destroyPermanents(pile2, game, source);
            }

            return true;
        }
        return false;
    }

    private void destroyPermanents(List<Permanent> pile, Game game, Ability source) {
        for (Permanent permanent : pile) {
            if (permanent != null) {
                permanent.destroy(source, game, true);
            }
        }
    }
}

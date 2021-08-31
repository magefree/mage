
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Quercitron
 */
public final class FadeAway extends CardImpl {

    public FadeAway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}");

        // For each creature, its controller sacrifices a permanent unless they pay {1}.
        this.getSpellAbility().addEffect(new FadeAwayEffect());
    }

    private FadeAway(final FadeAway card) {
        super(card);
    }

    @Override
    public FadeAway copy() {
        return new FadeAway(this);
    }
}

class FadeAwayEffect extends OneShotEffect {

    private static final FilterCreaturePermanent FILTER_CREATURE = new FilterCreaturePermanent();

    public FadeAwayEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "For each creature, its controller sacrifices a permanent unless they pay {1}";
    }

    public FadeAwayEffect(final FadeAwayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int creaturesNumber = game.getBattlefield().getAllActivePermanents(FILTER_CREATURE, playerId, game).size();
                if (creaturesNumber > 0) {
                    String message = "For how many creatures will you pay (up to " + creaturesNumber + ")?";
                    int payAmount = 0;
                    boolean paid = false;
                    while (player.canRespond() && !paid) {
                        payAmount = player.getAmount(0, creaturesNumber, message, game);
                        ManaCostsImpl cost = new ManaCostsImpl();
                        cost.add(new GenericManaCost(payAmount));
                        cost.clearPaid();
                        if (cost.payOrRollback(source, game, source, playerId)) {
                            paid = true;
                        }
                    }

                    int sacrificeNumber = creaturesNumber - payAmount;
                    game.informPlayers(player.getLogName() + " pays {" + payAmount + "} and sacrifices "
                            + sacrificeNumber + " permanent" + (sacrificeNumber == 1 ? "" : "s"));

                    if (sacrificeNumber > 0) {
                        int permanentsNumber = game.getBattlefield().getAllActivePermanents(playerId).size();
                        int targetsNumber = Math.min(sacrificeNumber, permanentsNumber);
                        TargetControlledPermanent target = new TargetControlledPermanent(targetsNumber);
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        for (UUID permanentId : target.getTargets()) {
                            Permanent permanent = game.getPermanent(permanentId);
                            if (permanent != null) {
                                permanent.sacrifice(source, game);
                            }
                        }
                    }
                } else {
                    game.informPlayers(player.getLogName() + " has no creatures");
                }
            }
        }
        return true;
    }

    @Override
    public Effect copy() {
        return new FadeAwayEffect(this);
    }
}

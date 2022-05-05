
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class CracklingDoom extends CardImpl {

    public CracklingDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}{B}");

        // Crackling Doom deals 2 damage to each opponent. Each opponent sacrifices a creature with the greatest power among creatures they control.
        this.getSpellAbility().addEffect(new DamagePlayersEffect(2, TargetController.OPPONENT));
        this.getSpellAbility().addEffect(new CracklingDoomEffect());

    }

    private CracklingDoom(final CracklingDoom card) {
        super(card);
    }

    @Override
    public CracklingDoom copy() {
        return new CracklingDoom(this);
    }
}

class CracklingDoomEffect extends OneShotEffect {

    public CracklingDoomEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "Each opponent sacrifices a creature with the greatest power among creatures they control";
    }

    public CracklingDoomEffect(final CracklingDoomEffect effect) {
        super(effect);
    }

    @Override
    public CracklingDoomEffect copy() {
        return new CracklingDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Permanent> toSacrifice = new ArrayList<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (controller.hasOpponent(playerId, game)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        int greatestPower = Integer.MIN_VALUE;
                        int numberOfCreatures = 0;
                        Permanent permanentToSacrifice = null;
                        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, playerId, game)) {
                            if (permanent.getPower().getValue() > greatestPower) {
                                greatestPower = permanent.getPower().getValue();
                                numberOfCreatures = 1;
                                permanentToSacrifice = permanent;
                            } else if (permanent.getPower().getValue() == greatestPower) {
                                numberOfCreatures++;
                            }
                        }
                        if (numberOfCreatures == 1) {
                            if (permanentToSacrifice != null) {
                                toSacrifice.add(permanentToSacrifice);
                            }
                        } else if (greatestPower != Integer.MIN_VALUE) {
                            FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature to sacrifice with power equal to " + greatestPower);
                            filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, greatestPower));
                            Target target = new TargetControlledCreaturePermanent(filter);
                            if (opponent.choose(outcome, target, source, game)) {
                                Permanent permanent = game.getPermanent(target.getFirstTarget());
                                if (permanent != null) {
                                    toSacrifice.add(permanent);
                                }
                            }
                        }
                    }
                }
            }
            for (Permanent permanent : toSacrifice) {
                permanent.sacrifice(source, game);
            }
            return true;
        }
        return false;
    }
}

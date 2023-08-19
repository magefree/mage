package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponentOrPlaneswalker;

import java.util.UUID;

/**
 * @author L_J
 */
public final class GoblinLyre extends CardImpl {

    public GoblinLyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Sacrifice Goblin Lyre: Flip a coin. If you win the flip, Goblin Lyre deals damage to target opponent equal to the number of creatures you control. If you lose the flip, Goblin Lyre deals damage to you equal to the number of creatures that opponent controls.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GoblinLyreEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        this.addAbility(ability);
    }

    private GoblinLyre(final GoblinLyre card) {
        super(card);
    }

    @Override
    public GoblinLyre copy() {
        return new GoblinLyre(this);
    }
}

class GoblinLyreEffect extends OneShotEffect {

    public GoblinLyreEffect() {
        super(Outcome.Damage);
        this.staticText = "Flip a coin. If you win the flip, {this} deals damage to target opponent or planeswalker equal to the number of creatures you control. "
                + "If you lose the flip, Goblin Lyre deals damage to you equal to the number of creatures that opponent or that planeswalker's controller controls";
    }

    public GoblinLyreEffect(final GoblinLyreEffect effect) {
        super(effect);
    }

    @Override
    public GoblinLyreEffect copy() {
        return new GoblinLyreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayerOrPlaneswalkerController(getTargetPointer().getFirst(game, source));
        if (controller != null) {
            if (controller.flipCoin(source, game, true)) {
                int damage = CreaturesYouControlCount.instance.calculate(game, source, this);
                if (opponent != null) {
                    return game.damagePlayerOrPermanent(source.getFirstTarget(), damage, source.getSourceId(), source, game, false, true) > 0;
                }
            } else {
                int damage = game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), opponent.getId(), game).size();
                return controller.damage(damage, source.getSourceId(), source, game) > 0;
            }
        }
        return false;
    }
}

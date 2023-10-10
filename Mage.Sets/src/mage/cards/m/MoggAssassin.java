

package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author L_J
 */
public final class MoggAssassin extends CardImpl {

    public MoggAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: You choose target creature an opponent controls, and that opponent chooses target creature. Flip a coin. If you win the flip, destroy the creature you chose. If you lose the flip, destroy the creature your opponent chose.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new MoggAssassinEffect(),
                new TapSourceCost()
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        ability.addTarget(new TargetCreaturePermanent());
        ability.setTargetAdjuster(MoggAssassinAdjuster.instance);
        this.addAbility(ability);
    }

    private MoggAssassin(final MoggAssassin card) {
        super(card);
    }

    @Override
    public MoggAssassin copy() {
        return new MoggAssassin(this);
    }

}

enum MoggAssassinAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player controller = game.getPlayer(ability.getControllerId());
        if (controller == null) {
            return;
        }
        UUID opponentId = null;
        if (game.getOpponents(controller.getId()).size() > 1) {
            Target target = ability.getTargets().get(0);
            if (controller.chooseTarget(Outcome.DestroyPermanent, target, ability, game)) {
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                opponentId = permanent.getControllerId();
            } else {
                opponentId = game.getOpponents(controller.getId()).iterator().next();
            }
        } else {
            opponentId = game.getOpponents(controller.getId()).iterator().next();
        }

        if (opponentId != null) {
            ability.getTargets().get(1).setTargetController(opponentId);
        }
    }
}

class MoggAssassinEffect extends OneShotEffect {

    public MoggAssassinEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "You choose target creature an opponent controls, and that opponent chooses target creature. Flip a coin. If you win the flip, destroy the creature you chose. If you lose the flip, destroy the creature your opponent chose";
    }

    private MoggAssassinEffect(final MoggAssassinEffect effect) {
        super(effect);
    }

    @Override
    public MoggAssassinEffect copy() {
        return new MoggAssassinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent chosenPermanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent opponentsPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (controller.flipCoin(source, game, true)) {
            if (chosenPermanent != null) {
                chosenPermanent.destroy(source, game, false);
                return true;
            }
        } else {
            if (opponentsPermanent != null) {
                opponentsPermanent.destroy(source, game, false);
                return true;
            }
        }
        return false;
    }
}

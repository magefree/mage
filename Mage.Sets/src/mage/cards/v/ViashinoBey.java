package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 */
public final class ViashinoBey extends CardImpl {

    private static final String rule = "If {this} attacks, all creatures you control attack if able.";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ViashinoBey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // If Viashino Bey attacks, all creatures you control attack if able.
        this.addAbility(new AttacksTriggeredAbility(new ViashinoBeyEffect(), false, rule));

    }

    private ViashinoBey(final ViashinoBey card) {
        super(card);
    }

    @Override
    public ViashinoBey copy() {
        return new ViashinoBey(this);
    }
}

class ViashinoBeyEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public ViashinoBeyEffect() {
        super(Outcome.Benefit);
    }

    public ViashinoBeyEffect(final ViashinoBeyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        TargetOpponent targetDefender = new TargetOpponent();
        if (controller != null) {
            game.getBattlefield().getAllActivePermanents(CardType.CREATURE, game).stream().filter((permanent) -> (filter.match(permanent, source.getControllerId(), source, game))).forEachOrdered((permanent) -> {
                if (game.getOpponents(controller.getId()).size() > 1) {
                    controller.choose(outcome.Benefit, targetDefender, source, game);
                } else {
                    targetDefender.add(game.getOpponents(controller.getId()).iterator().next(), game);
                }
                if (permanent.canAttack(targetDefender.getFirstTarget(), game)) {
                    controller.declareAttacker(permanent.getId(), targetDefender.getFirstTarget(), game, false);
                }
            });
        }
        return false;
    }

    @Override
    public ViashinoBeyEffect copy() {
        return new ViashinoBeyEffect(this);
    }
}

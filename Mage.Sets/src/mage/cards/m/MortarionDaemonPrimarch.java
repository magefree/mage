package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.ManaUtil;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;
import mage.game.permanent.token.BlackAstartesWarriorToken;

/**
 * @author TheElk801
 */
public final class MortarionDaemonPrimarch extends CardImpl {

    public MortarionDaemonPrimarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.PRIMARCH);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Primarch of the Death Guard -- At the beginning of your end step, you may pay {X}. If you do, create X 2/2 black Astartes Warrior creature tokens with menace. X can't be greater than the amount of life you lost this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new MortarionDaemonPrimarchEffect(), TargetController.YOU, false
        ).withFlavorWord("Primarch of the Death Guard"));
    }

    private MortarionDaemonPrimarch(final MortarionDaemonPrimarch card) {
        super(card);
    }

    @Override
    public MortarionDaemonPrimarch copy() {
        return new MortarionDaemonPrimarch(this);
    }
}

class MortarionDaemonPrimarchEffect extends OneShotEffect {

    MortarionDaemonPrimarchEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}. If you do, create X 2/2 black Astartes Warrior creature tokens with menace. " +
                "X can't be greater than the amount of life you lost this turn";
    }

    private MortarionDaemonPrimarchEffect(final MortarionDaemonPrimarchEffect effect) {
        super(effect);
    }

    @Override
    public MortarionDaemonPrimarchEffect copy() {
        return new MortarionDaemonPrimarchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int lifeLost = game
                .getState()
                .getWatcher(PlayerLostLifeWatcher.class)
                .getLifeLost(source.getControllerId());
        Player player = game.getPlayer(source.getControllerId());
        if (lifeLost < 1 || player == null) {
            return false;
        }
        int manaPaid = ManaUtil.playerPaysXGenericMana(
                true, "Mortarion, Daemon Primarch", player, source, game, lifeLost
        );
        return manaPaid > 0 && new BlackAstartesWarriorToken().putOntoBattlefield(manaPaid, game, source);
    }
}

package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.common.ControllerGotLifeCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.ManaUtil;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShannaPurifyingBlade extends CardImpl {

    public ShannaPurifyingBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, you may pay {X}. If you do, draw X cards. X can't be greater than the amount of life you gained this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ShannaPurifyingBladeEffect(), TargetController.YOU, false
        ).addHint(ControllerGotLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private ShannaPurifyingBlade(final ShannaPurifyingBlade card) {
        super(card);
    }

    @Override
    public ShannaPurifyingBlade copy() {
        return new ShannaPurifyingBlade(this);
    }
}

class ShannaPurifyingBladeEffect extends OneShotEffect {

    ShannaPurifyingBladeEffect() {
        super(Outcome.Benefit);
        staticText = "you may pay {X}. If you do, draw X cards. " +
                "X can't be greater than the amount of life you gained this turn";
    }

    private ShannaPurifyingBladeEffect(final ShannaPurifyingBladeEffect effect) {
        super(effect);
    }

    @Override
    public ShannaPurifyingBladeEffect copy() {
        return new ShannaPurifyingBladeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int lifeGained = ControllerGotLifeCount.instance.calculate(game, source, this);
        if (lifeGained < 1) {
            return false;
        }
        int count = ManaUtil.playerPaysXGenericMana(
                true, "Shanna, Purifying Blade",
                player, source, game, lifeGained
        );
        return count > 0 && player.drawCards(count, source, game) > 0;
    }
}

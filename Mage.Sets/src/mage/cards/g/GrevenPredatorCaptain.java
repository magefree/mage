package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrevenPredatorCaptain extends CardImpl {

    public GrevenPredatorCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility());

        // Greven, Predator Captain gets +X/+0, where X is the amount of life you've lost this turn.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                GrevenPredatorCaptainValue.instance, StaticValue.getZeroValue(), Duration.WhileOnBattlefield
        )));

        // Whenever Greven attacks, you may sacrifice another creature. If you do, you draw cards equal to that creature's power and you lose life equal to that creature's toughness.
        this.addAbility(new AttacksTriggeredAbility(new GrevenPredatorCaptainEffect(), true));
    }

    private GrevenPredatorCaptain(final GrevenPredatorCaptain card) {
        super(card);
    }

    @Override
    public GrevenPredatorCaptain copy() {
        return new GrevenPredatorCaptain(this);
    }
}

enum GrevenPredatorCaptainValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (watcher != null) {
            return watcher.getLifeLost(sourceAbility.getControllerId());
        }
        return 0;
    }

    @Override
    public GrevenPredatorCaptainValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "life you've lost this turn";
    }
}

class GrevenPredatorCaptainEffect extends OneShotEffect {

    GrevenPredatorCaptainEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice another creature. If you do, you draw cards equal to " +
                "that creature's power and you lose life equal to that creature's toughness.";
    }

    private GrevenPredatorCaptainEffect(final GrevenPredatorCaptainEffect effect) {
        super(effect);
    }

    @Override
    public GrevenPredatorCaptainEffect copy() {
        return new GrevenPredatorCaptainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE, true
        );
        if (!player.choose(outcome, target, source.getSourceId(), game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        int toughness = permanent.getToughness().getValue();
        if (!permanent.sacrifice(source.getSourceId(), game)) {
            return false;
        }
        player.drawCards(power, game);
        player.loseLife(toughness, game, false);
        return true;
    }
}
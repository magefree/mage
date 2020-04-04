package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WingfoldPteron extends CardImpl {

    public WingfoldPteron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Wingfold Pteron enters the battlefield with your choice of a flying counter or a hexproof counter on it.
        this.addAbility(new EntersBattlefieldAbility(new WingfoldPteronEffect()));
    }

    private WingfoldPteron(final WingfoldPteron card) {
        super(card);
    }

    @Override
    public WingfoldPteron copy() {
        return new WingfoldPteron(this);
    }
}

class WingfoldPteronEffect extends OneShotEffect {

    WingfoldPteronEffect() {
        super(Outcome.Benefit);
        staticText = "with your choice of a flying counter or a hexproof counter on it";
    }

    private WingfoldPteronEffect(final WingfoldPteronEffect effect) {
        super(effect);
    }

    @Override
    public WingfoldPteronEffect copy() {
        return new WingfoldPteronEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        Counter counter;
        if (player.chooseUse(
                Outcome.Neutral, "Choose flying or hexproof", null,
                "Flying", "Hexproof", source, game
        )) {
            counter = CounterType.FLYING.createInstance();
        } else {
            counter = CounterType.HEXPROOF.createInstance();
        }
        return permanent.addCounters(counter, source, game);
    }
}
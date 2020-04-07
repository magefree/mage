package mage.cards.h;

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
public final class HelicaGlider extends CardImpl {

    public HelicaGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.SQUIRREL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Helica Glider enters the battlfield with your choice of a flying counter or a first strike counter on it.
        this.addAbility(new EntersBattlefieldAbility(new HelicaGliderEffect()));
    }

    private HelicaGlider(final HelicaGlider card) {
        super(card);
    }

    @Override
    public HelicaGlider copy() {
        return new HelicaGlider(this);
    }
}

class HelicaGliderEffect extends OneShotEffect {

    HelicaGliderEffect() {
        super(Outcome.Benefit);
        staticText = "with your choice of a flying counter or a first strike counter on it";
    }

    private HelicaGliderEffect(final HelicaGliderEffect effect) {
        super(effect);
    }

    @Override
    public HelicaGliderEffect copy() {
        return new HelicaGliderEffect(this);
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
                Outcome.Neutral, "Choose flying or first strike", null,
                "Flying", "First strike", source, game
        )) {
            counter = CounterType.FLYING.createInstance();
        } else {
            counter = CounterType.FIRST_STRIKE.createInstance();
        }
        return permanent.addCounters(counter, source, game);
    }
}
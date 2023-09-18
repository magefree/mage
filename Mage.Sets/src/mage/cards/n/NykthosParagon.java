package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class NykthosParagon extends CardImpl {

    public NykthosParagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Whenever you gain life, you may put that many +1/+1 counters on each creature you control. Do this only once each turn.
        this.addAbility(new GainLifeControllerTriggeredAbility(new NykthosParagonEffect(), true, true).setDoOnlyOnceEachTurn(true));
    }

    private NykthosParagon(final NykthosParagon card) {
        super(card);
    }

    @Override
    public NykthosParagon copy() {
        return new NykthosParagon(this);
    }
}

class NykthosParagonEffect extends OneShotEffect {

    public NykthosParagonEffect() {
        super(Outcome.BoostCreature);
        staticText = "put that many +1/+1 counters on each creature you control";
    }

    private NykthosParagonEffect(final NykthosParagonEffect effect) {
        super(effect);
    }

    @Override
    public NykthosParagonEffect copy() {
        return new NykthosParagonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Integer life = (Integer) this.getValue("gainedLife");
        if (controller == null || life == null || life < 1) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURES,
                source.getControllerId(), source, game
        )) {
            permanent.addCounters(
                    CounterType.P1P1.createInstance(life),
                    source.getControllerId(), source, game
            );
            game.informPlayers(
                    CardUtil.getSourceLogName(game, source) + ": " +
                            controller.getLogName() + " puts " + life +
                            " +1/+1 counters on " + permanent.getLogName()
            );
        }
        return true;
    }
}

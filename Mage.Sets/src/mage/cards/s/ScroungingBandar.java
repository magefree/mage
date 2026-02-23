package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_ANOTHER_TARGET_CREATURE;

/**
 *
 * @author Styxo
 */
public final class ScroungingBandar extends CardImpl {

    public ScroungingBandar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.MONKEY);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Scrounging Bandar enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.P1P1.createInstance(2)));

        // At the beginning of you upkeep, you may move any number of +1/+1 counters from Scrounging Bandar onto another target creature.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ScroungingBandarEffect(), true);
        ability.addTarget(new TargetPermanent(FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private ScroungingBandar(final ScroungingBandar card) {
        super(card);
    }

    @Override
    public ScroungingBandar copy() {
        return new ScroungingBandar(this);
    }
}

class ScroungingBandarEffect extends OneShotEffect {

    ScroungingBandarEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may move any number of +1/+1 counters from {this} onto another target creature";
    }

    private ScroungingBandarEffect(final ScroungingBandarEffect effect) {
        super(effect);
    }

    @Override
    public ScroungingBandarEffect copy() {
        return new ScroungingBandarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent fromPermanent = game.getPermanent(source.getSourceId());
            Permanent toPermanent = game.getPermanent(source.getTargets().getFirstTarget());

            if (fromPermanent != null && toPermanent != null) {
                int amountCounters = fromPermanent.getCounters(game).getCount(CounterType.P1P1);
                if (amountCounters > 0) {
                    int amountToMove = controller.getAmount(0, amountCounters, "How many counters do you want to move?", source, game);
                    if (amountToMove > 0) {
                        fromPermanent.removeCounters(CounterType.P1P1.createInstance(amountToMove), source, game);
                        toPermanent.addCounters(CounterType.P1P1.createInstance(amountToMove), source.getControllerId(), source, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}

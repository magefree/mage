package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetDiscard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class MindMaggots extends CardImpl {

    public MindMaggots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Mind Maggots enters the battlefield, discard any number of creature cards.
        // For each card discarded this way, put two +1/+1 counters on Mind Maggots.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MindMaggotsEffect()));
    }

    private MindMaggots(final MindMaggots card) {
        super(card);
    }

    @Override
    public MindMaggots copy() {
        return new MindMaggots(this);
    }
}

class MindMaggotsEffect extends OneShotEffect {

    MindMaggotsEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "discard any number of creature cards. " +
                "For each card discarded this way, put two +1/+1 counters on {this}";
    }

    private MindMaggotsEffect(final MindMaggotsEffect effect) {
        super(effect);
    }

    @Override
    public MindMaggotsEffect copy() {
        return new MindMaggotsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCard target = new TargetDiscard(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CREATURE, controller.getId());
        controller.choose(outcome, controller.getHand(), target, source, game);
        int counters = controller.discard(new CardsImpl(target.getTargets()), false, source, game).size() * 2;
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null || counters < 1) {
            return true;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(counters), source.getControllerId(), source, game);
        return true;
    }
}

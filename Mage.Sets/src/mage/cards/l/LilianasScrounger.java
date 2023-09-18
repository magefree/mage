package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LilianasScrounger extends CardImpl {

    public LilianasScrounger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // At the beginning of each end step, if a creature died this turn, you may put a loyalty counter on a Liliana planeswalker you control.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfEndStepTriggeredAbility(
                new LilianasScroungerEffect(), TargetController.ANY, false
        ), MorbidCondition.instance, "At the beginning of each end step, " +
                "if a creature died this turn, you may put a loyalty counter on a Liliana planeswalker you control."
        ).addHint(MorbidHint.instance));
    }

    private LilianasScrounger(final LilianasScrounger card) {
        super(card);
    }

    @Override
    public LilianasScrounger copy() {
        return new LilianasScrounger(this);
    }
}

class LilianasScroungerEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledPlaneswalkerPermanent(SubType.LILIANA, "Liliana planeswalker you control");

    LilianasScroungerEffect() {
        super(Outcome.Benefit);
    }

    private LilianasScroungerEffect(final LilianasScroungerEffect effect) {
        super(effect);
    }

    @Override
    public LilianasScroungerEffect copy() {
        return new LilianasScroungerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || game.getBattlefield().count(
                filter, source.getControllerId(), source, game
        ) < 1 || !player.chooseUse(
                outcome, "Put a loyalty counter on a Liliana planeswalker you control?", source, game
        )) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 1, filter, true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.addCounters(CounterType.LOYALTY.createInstance(), source.getControllerId(), source, game);
    }
}

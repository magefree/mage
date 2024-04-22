package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.DescendedThisTurnCondition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.DescendedThisTurnCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RemoveAllCountersSourceEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.DescendedWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BrasssTunnelGrinder extends CardImpl {

    public BrasssTunnelGrinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{R}");
        this.secondSideCardClazz = mage.cards.t.TecutlanTheSearingRift.class;

        this.supertype.add(SuperType.LEGENDARY);

        // When Brass's Tunnel-Grinder enters the battlefield, discard any number of cards, then draw that many cards plus one.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BrasssTunnelGrinderEffect()));

        // At the beginning of your end step, if you descended this turn, put a bore counter on Brass's Tunnel-Grinder. Then if there are three or more bore counters on it, remove those counters and transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.BORE.createInstance()),
                TargetController.YOU, DescendedThisTurnCondition.instance, false
        );

        ConditionalOneShotEffect secondCheck = new ConditionalOneShotEffect(
                new RemoveAllCountersSourceEffect(CounterType.BORE),
                new SourceHasCounterCondition(CounterType.BORE, 3, Integer.MAX_VALUE),
                "Then if there are three or more bore counters on it, remove those counters and transform it"
        );
        secondCheck.addEffect(new TransformSourceEffect());
        ability.addEffect(secondCheck);
        ability.addHint(DescendedThisTurnCount.getHint());
        this.addAbility(ability, new DescendedWatcher());
    }

    private BrasssTunnelGrinder(final BrasssTunnelGrinder card) {
        super(card);
    }

    @Override
    public BrasssTunnelGrinder copy() {
        return new BrasssTunnelGrinder(this);
    }
}

class BrasssTunnelGrinderEffect extends OneShotEffect {

    BrasssTunnelGrinderEffect() {
        super(Outcome.DrawCard);
        staticText = "discard any number of cards, then draw that many cards plus one";
    }

    private BrasssTunnelGrinderEffect(final BrasssTunnelGrinderEffect effect) {
        super(effect);
    }

    @Override
    public BrasssTunnelGrinderEffect copy() {
        return new BrasssTunnelGrinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        int dicarded = player.discard(0, Integer.MAX_VALUE, false, source, game).size();
        player.drawCards(1 + dicarded, source, game);
        return true;
    }
}

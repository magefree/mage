package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class KellanJoinsUp extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public KellanJoinsUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Kellan Joins Up enters the battlefield, you may exile a nonland card with mana value 3 or less from your hand. If you do, it becomes plotted.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KellanJoinsUpEffect()));

        // Whenever a legendary creature enters the battlefield under your control, put a +1/+1 counter on each creature you control.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(),
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ), filter
        ));
    }

    private KellanJoinsUp(final KellanJoinsUp card) {
        super(card);
    }

    @Override
    public KellanJoinsUp copy() {
        return new KellanJoinsUp(this);
    }
}

class KellanJoinsUpEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("nonland card with mana value 3 or less");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public KellanJoinsUpEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may exile a nonland card with mana value 3 or less from your hand. If you do, it becomes plotted";
    }

    private KellanJoinsUpEffect(final KellanJoinsUpEffect effect) {
        super(effect);
    }

    @Override
    public KellanJoinsUpEffect copy() {
        return new KellanJoinsUpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInHand target = new TargetCardInHand(0, 1, filter);
        if (player.chooseTarget(outcome, target, source, game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                PlotAbility.doExileAndPlotCard(card, game, source);
            }
        }
        return true;
    }
}

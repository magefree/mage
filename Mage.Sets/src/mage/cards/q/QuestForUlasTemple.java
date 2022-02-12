package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class QuestForUlasTemple extends CardImpl {

    private static final FilterCard filter
            = new FilterCreatureCard("Kraken, Leviathan, Octopus, or Serpent creature card");

    static {
        filter.add(Predicates.or(
                SubType.KRAKEN.getPredicate(),
                SubType.LEVIATHAN.getPredicate(),
                SubType.OCTOPUS.getPredicate(),
                SubType.SERPENT.getPredicate()
        ));
    }

    private static final Condition condition = new SourceHasCounterCondition(CounterType.QUEST, 3);

    public QuestForUlasTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // At the beginning of your upkeep, you may look at the top card of your library. If it's a creature card, you may reveal it and put a quest counter on Quest for Ula's Temple.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new QuestForUlasTempleEffect(), TargetController.YOU, true
        ));

        // At the beginning of each end step, if there are three or more quest counters on Quest for Ula's Temple, you may put a Kraken, Leviathan, Octopus, or Serpent creature card from your hand onto the battlefield.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new PutCardFromHandOntoBattlefieldEffect(filter),
                TargetController.ANY, condition, false
        ));
    }

    private QuestForUlasTemple(final QuestForUlasTemple card) {
        super(card);
    }

    @Override
    public QuestForUlasTemple copy() {
        return new QuestForUlasTemple(this);
    }
}

class QuestForUlasTempleEffect extends OneShotEffect {

    QuestForUlasTempleEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may look at the top card of your library. " +
                "If it's a creature card, you may reveal it and put a quest counter on {this}";
    }

    private QuestForUlasTempleEffect(final QuestForUlasTempleEffect effect) {
        super(effect);
    }

    @Override
    public QuestForUlasTempleEffect copy() {
        return new QuestForUlasTempleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        controller.lookAtCards("Top of library", card, game);
        if (!card.isCreature(game) || !controller.chooseUse(
                Outcome.DrawCard, "Reveal the top card of your library?", source, game
        )) {
            return true;
        }
        controller.revealCards(source, new CardsImpl(card), game);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.addCounters(
                    CounterType.QUEST.createInstance(),
                    source.getControllerId(), source, game
            );
        }
        return true;
    }
}

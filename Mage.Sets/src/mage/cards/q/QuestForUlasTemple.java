
package mage.cards.q;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class QuestForUlasTemple extends CardImpl {

    public QuestForUlasTemple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        // At the beginning of your upkeep, you may look at the top card of your library. If it's a creature card, you may reveal it and put a quest counter on Quest for Ula's Temple.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new QuestForUlasTempleEffect(), TargetController.YOU, true));

        // At the beginning of each end step, if there are three or more quest counters on Quest for Ula's Temple, you may put a Kraken, Leviathan, Octopus, or Serpent creature card from your hand onto the battlefield.
        FilterCreatureCard filter = new FilterCreatureCard("Kraken, Leviathan, Octopus, or Serpent creature card");
        filter.add(Predicates.or(
                SubType.KRAKEN.getPredicate(),
                SubType.LEVIATHAN.getPredicate(),
                SubType.OCTOPUS.getPredicate(),
                SubType.SERPENT.getPredicate()));
        this.addAbility(new QuestForUlasTempleTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(filter)));
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

    public QuestForUlasTempleEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may look at the top card of your library. If it's a creature card, you may reveal it and put a quest counter on {this}";
    }

    public QuestForUlasTempleEffect(final QuestForUlasTempleEffect effect) {
        super(effect);
    }

    @Override
    public QuestForUlasTempleEffect copy() {
        return new QuestForUlasTempleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && controller != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            controller.lookAtCards(sourcePermanent.getName(), cards, game);
            if (card.isCreature()) {
                if (controller.chooseUse(Outcome.DrawCard, "Reveal the top card of your library?", source, game)) {
                    controller.revealCards(sourcePermanent.getName(), cards, game);
                    Permanent questForUlasTemple = game.getPermanent(source.getSourceId());
                    if (questForUlasTemple != null) {
                        questForUlasTemple.addCounters(CounterType.QUEST.createInstance(), source.getControllerId(), source, game);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class QuestForUlasTempleTriggeredAbility extends TriggeredAbilityImpl {

    public QuestForUlasTempleTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect, true);
    }

    public QuestForUlasTempleTriggeredAbility(final QuestForUlasTempleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public QuestForUlasTempleTriggeredAbility copy() {
        return new QuestForUlasTempleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent quest = game.getPermanent(super.getSourceId());
        return quest != null && quest.getCounters(game).getCount(CounterType.QUEST) >= 3;
    }

    @Override
    public String getRule() {
        return "At the beginning of each end step, if there are three or more quest counters on {this}, " + super.getRule();
    }
}


package mage.cards.g;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class GrimReminder extends CardImpl {

    public GrimReminder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Search your library for a nonland card and reveal it. Each opponent who cast a card this turn with the same name as that card loses 6 life. Then shuffle your library.
        this.getSpellAbility().addEffect(new GrimReminderEffect());
        this.getSpellAbility().addWatcher(new GrimReminderWatcher());

        // {B}{B}: Return Grim Reminder from your graveyard to your hand. Activate this ability only during your upkeep.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToHandEffect(),
                new ManaCostsImpl<>("{B}{B}"),
                new IsStepCondition(PhaseStep.UPKEEP),
                null
        ));
    }

    private GrimReminder(final GrimReminder card) {
        super(card);
    }

    @Override
    public GrimReminder copy() {
        return new GrimReminder(this);
    }
}

class GrimReminderEffect extends OneShotEffect {

    GrimReminderEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for a nonland card and reveal it. "
                + "Each opponent who cast a spell this turn with the same name as that card loses 6 life. "
                + "Then shuffle.";
    }

    GrimReminderEffect(final GrimReminderEffect effect) {
        super(effect);
    }

    @Override
    public GrimReminderEffect copy() {
        return new GrimReminderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_NON_LAND);
            if (controller.searchLibrary(target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    Cards cardsToReveal = new CardsImpl(card);
                    controller.revealCards(sourceObject.getIdName(), cardsToReveal, game);
                    String cardName = card.getName();
                    GrimReminderWatcher watcher = game.getState().getWatcher(GrimReminderWatcher.class);
                    if (watcher != null) {
                        for (UUID playerId : watcher.getPlayersCastSpell(cardName)) {
                            Player player = game.getPlayer(playerId);
                            if (player != null) {
                                player.loseLife(6, game, source, false);
                            }
                        }
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}

class GrimReminderWatcher extends Watcher {

    private final Map<String, Set<UUID>> playersCastSpell = new HashMap<>();

    public GrimReminderWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            MageObject spell = game.getObject(event.getTargetId());
            UUID playerId = event.getPlayerId();
            if (playerId != null && spell != null) {
                playersCastSpell.putIfAbsent(spell.getName(), new HashSet<>());
                playersCastSpell.get(spell.getName()).add(playerId);
            }
        }
    }

    @Override
    public void reset() {
        playersCastSpell.clear();
    }

    public Set<UUID> getPlayersCastSpell(String spellName) {
        return playersCastSpell.getOrDefault(spellName, new HashSet<>());
    }

}

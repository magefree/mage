package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrimReminder extends CardImpl {

    public GrimReminder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Search your library for a nonland card and reveal it. Each opponent who cast a card this turn with the same name as that card loses 6 life. Then shuffle your library.
        this.getSpellAbility().addEffect(new GrimReminderEffect());
        this.getSpellAbility().addWatcher(new SpellsCastWatcher());

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

    private GrimReminderEffect(final GrimReminderEffect effect) {
        super(effect);
    }

    @Override
    public GrimReminderEffect copy() {
        return new GrimReminderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_NON_LAND);
        controller.searchLibrary(target, source, game);
        Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
        if (card == null) {
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.revealCards(source, new CardsImpl(card), game);
        String cardName = card.getName();
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null && watcher
                    .getSpellsCastThisTurn(playerId)
                    .stream()
                    .anyMatch(spell -> spell.sharesName(card, game))) {
                player.loseLife(6, game, source, false);
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}

package mage.abilities.keyword;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInExile;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class CascadeAbility extends TriggeredAbilityImpl {
    //20091005 - 702.82

    private static final String REMINDERTEXT = " <i>(When you cast this spell, "
            + "exile cards from the top of your library until you exile a "
            + "nonland card that costs less."
            + " You may cast it without paying its mana cost. "
            + "Put the exiled cards on the bottom in a random order.)</i>";
    private final boolean withReminder;
    private static final FilterCard filter = new FilterLandCard("land card (to put onto the battlefield)");

    public CascadeAbility() {
        this(true);
    }

    public CascadeAbility(boolean withReminder) {
        super(Zone.STACK, new CascadeEffect());
        this.withReminder = withReminder;
    }

    private CascadeAbility(final CascadeAbility ability) {
        super(ability);
        this.withReminder = ability.withReminder;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null
                && spell.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("cascade");
        if (withReminder) {
            sb.append(REMINDERTEXT);
        }
        return sb.toString();
    }

    @Override
    public CascadeAbility copy() {
        return new CascadeAbility(this);
    }

}

class CascadeEffect extends OneShotEffect {

    CascadeEffect() {
        super(Outcome.PutCardInPlay);
    }

    private CascadeEffect(CascadeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        int sourceCost = card.getConvertedManaCost();
        do {
            card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                break;
            }
            cards.add(card);
            controller.moveCards(card, Zone.EXILED, source, game);
        } while (controller.canRespond()
                && (card.isLand() || card.getConvertedManaCost() >= sourceCost));

        controller.getLibrary().reset(); // set back empty draw state if that caused an empty draw

        GameEvent event = GameEvent.getEvent(GameEvent.EventType.CASCADE_LAND, source.getSourceId(), source, source.getControllerId(), 0);
        game.replaceEvent(event);
        if (event.getAmount() > 0) {
            TargetCardInExile target = new TargetCardInExile(
                    0, event.getAmount(), StaticFilters.FILTER_CARD_LAND, null, true
            );
            controller.choose(Outcome.PutCardInPlay, cards, target, game);
            controller.moveCards(
                    new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD,
                    source, game, true, false, false, null
            );
        }
        if (card != null && controller.chooseUse(
                outcome, "Use cascade effect on " + card.getLogName() + '?', source, game
        )) {
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            controller.cast(controller.chooseAbilityForCast(card, game, true),
                    game, true, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
        }
        // Move the remaining cards to the buttom of the library in a random order
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.EXILED);
        return controller.putCardsOnBottomOfLibrary(cards, game, source, false);
    }

    @Override
    public CascadeEffect copy() {
        return new CascadeEffect(this);
    }
}

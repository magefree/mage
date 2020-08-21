package mage.abilities.keyword;

import mage.ApprovingObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;

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
    private boolean withReminder;

    public CascadeAbility() {
        this(true);
    }

    public CascadeAbility(boolean withReminder) {
        super(Zone.STACK, new CascadeEffect());
        this.withReminder = withReminder;
    }

    public CascadeAbility(final CascadeAbility ability) {
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
        StringBuilder sb = new StringBuilder("Cascade");
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

    public CascadeEffect() {
        super(Outcome.PutCardInPlay);
    }

    public CascadeEffect(CascadeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ExileZone exile = game.getExile().createZone(source.getSourceId(),
                controller.getName() + " Cascade");
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
            controller.moveCardsToExile(card, source, game, true, exile.getId(), exile.getName());
        } while (controller.canRespond()
                && (card.isLand()
                || !cardThatCostsLess(sourceCost, card, game)));

        controller.getLibrary().reset(); // set back empty draw state if that caused an empty draw

        if (card != null) {
            if (controller.chooseUse(outcome, "Use cascade effect on " + card.getLogName() + '?', source, game)) {
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                controller.cast(controller.chooseAbilityForCast(card, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
            }
        }
        // Move the remaining cards to the buttom of the library in a random order
        return controller.putCardsOnBottomOfLibrary(new CardsImpl(exile), game, source, false);
    }

    @Override
    public CascadeEffect copy() {
        return new CascadeEffect(this);
    }

    private boolean cardThatCostsLess(int value, Card card, Game game) {

        return card.getConvertedManaCost() < value;

    }
}

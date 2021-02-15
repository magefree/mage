package mage.abilities.keyword;

import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
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
    //20210215 - 702.84a - Updated Cascade rule

    private static final String REMINDERTEXT = " <i>(When you cast this spell, "
            + "exile cards from the top of your library until you exile a "
            + "nonland card whose converted mana cost is less than this spell's converted mana cost. "
            + "You may cast that spell without paying its mana cost "
            + "if its converted mana cost is less than this spell's converted mana cost. "
            + "Then put all cards exiled this way that weren't cast on the bottom of your library in a random order.)</i>";
    private final boolean withReminder;

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
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card sourceCard = game.getCard(source.getSourceId());
        if (sourceCard == null) {
            return false;
        }
        Cards cardsToExile = new CardsImpl();
        int sourceCost = sourceCard.getConvertedManaCost();
        Card cardToCast = null;
        for (Card card : controller.getLibrary().getCards(game)) {
            cardsToExile.add(card);
            if (!card.isLand() && card.getConvertedManaCost() < sourceCost) {
                cardToCast = card;
                break;
            }
        }

        controller.moveCards(cardsToExile, Zone.EXILED, source, game);
        controller.getLibrary().reset(); // set back empty draw state if that caused an empty draw

        GameEvent event = GameEvent.getEvent(GameEvent.EventType.CASCADE_LAND, source.getSourceId(), source, source.getControllerId(), 0);
        game.replaceEvent(event);
        if (event.getAmount() > 0) {
            TargetCardInExile target = new TargetCardInExile(
                    0, event.getAmount(), StaticFilters.FILTER_CARD_LAND, null, true
            );
            controller.choose(Outcome.PutCardInPlay, cardsToExile, target, game);
            controller.moveCards(
                    new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD,
                    source, game, true, false, false, null
            );
        }
        if (cardToCast != null && controller.chooseUse(
                outcome, "Use cascade effect on " + cardToCast.getLogName() + '?', source, game
        )) {
            // Check to see if player is allowed to cast the back half
            // Front half is already checked by exile effect
            if (cardToCast instanceof ModalDoubleFacesCard) {
                ModalDoubleFacesCardHalf leftHalf = ((ModalDoubleFacesCard) cardToCast).getLeftHalfCard();
                ModalDoubleFacesCardHalf rightHalf = ((ModalDoubleFacesCard) cardToCast).getRightHalfCard();
                if (rightHalf.getConvertedManaCost() < sourceCost) {
                    castForFree(cardToCast, source, game, controller);
                } else {
                    castForFree(leftHalf, source, game, controller);
                }
            } else if (cardToCast instanceof AdventureCard) {
                Card adventureSpell = ((AdventureCard) cardToCast).getSpellCard();
                if (adventureSpell.getConvertedManaCost() < sourceCost) {
                    castForFree(cardToCast, source, game, controller);
                } else {
                    game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), Boolean.TRUE);
                    controller.cast(cardToCast.getSpellAbility(), game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), null);
                }
            } else {
                castForFree(cardToCast, source, game, controller);
            }
        }
        // Move the remaining cards to the buttom of the library in a random order
        cardsToExile.removeIf(uuid -> game.getState().getZone(uuid) != Zone.EXILED);
        return controller.putCardsOnBottomOfLibrary(cardsToExile, game, source, false);
    }

    private void castForFree(Card cardToCast, Ability source, Game game, Player controller) {
        game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), Boolean.TRUE);
        controller.cast(controller.chooseAbilityForCast(cardToCast, game, true),
                game, true, new ApprovingObject(source, game));
        game.getState().setValue("PlayFromNotOwnHandZone" + cardToCast.getId(), null);
    }

    @Override
    public CascadeEffect copy() {
        return new CascadeEffect(this);
    }
}

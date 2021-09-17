package mage.abilities.keyword;

import mage.ApprovingObject;
import mage.MageObject;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Cascade A keyword ability that may let a player cast a random extra spell for
 * no cost. See rule 702.84, “Cascade.”
 * <p>
 * 702.84. Cascade
 * <p>
 * 702.84a Cascade is a triggered ability that functions only while the spell
 * with cascade is on the stack. “Cascade” means “When you cast this spell,
 * exile cards from the top of your library until you exile a nonland card whose
 * converted mana cost is less than this spell’s converted mana cost. You may
 * cast that card without paying its mana cost. Then put all cards exiled this
 * way that weren’t cast on the bottom of your library in a random order.”
 * <p>
 * 702.84b If an effect allows a player to take an action with one or more of
 * the exiled cards “as you cascade,” the player may take that action after they
 * have finished exiling cards due to the cascade ability. This action is taken
 * before choosing whether to cast the last exiled card or, if no appropriate
 * card was exiled, before putting the exiled cards on the bottom of their
 * library in a random order.
 * <p>
 * 702.84c If a spell has multiple instances of cascade, each triggers
 * separately.
 *
 * @author BetaSteward_at_googlemail.com
 */
public class CascadeAbility extends TriggeredAbilityImpl {
    //20091005 - 702.82
    //20210215 - 702.84a - Updated Cascade rule

    // can't use singletone due rules:
    // 702.84c If a spell has multiple instances of cascade, each triggers separately.
    private static final String REMINDERTEXT = " <i>(When you cast this spell, "
            + "exile cards from the top of your library until you exile a "
            + "nonland card whose mana value is less than this spell's mana value. "
            + "You may cast that spell without paying its mana cost "
            + "if its mana value is less than this spell's mana value. "
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

        // exile cards from the top of your library until you exile a nonland card whose converted mana cost is less than this spell's converted mana cost
        Cards cardsToExile = new CardsImpl();
        int sourceCost = sourceCard.getManaValue();
        Card cardToCast = null;
        for (Card card : controller.getLibrary().getCards(game)) {
            cardsToExile.add(card);
            // the card move is sequential, not all at once.
            controller.moveCards(card, Zone.EXILED, source, game);
            game.getState().processAction(game);  // Laelia, the Blade Reforged
            if (!card.isLand(game)
                    && card.getManaValue() < sourceCost) {
                cardToCast = card;
                break;
            }
        }
        controller.getLibrary().reset(); // set back empty draw state if that caused an empty draw

        // additional replacement effect: As you cascade, you may put a land card from among the exiled cards onto the battlefield tapped
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.CASCADE_LAND, source.getSourceId(), source, source.getControllerId(), 0);
        game.replaceEvent(event);
        if (event.getAmount() > 0) {
            TargetCardInExile target = new TargetCardInExile(0, event.getAmount(), StaticFilters.FILTER_CARD_LAND, null, true);
            target.withChooseHint("land to put onto battlefield tapped");
            controller.choose(Outcome.PutCardInPlay, cardsToExile, target, game);
            controller.moveCards(
                    new CardsImpl(target.getTargets()).getCards(game), Zone.BATTLEFIELD,
                    source, game, true, false, false, null
            );
        }

        // You may cast that spell without paying its mana cost if its converted mana cost is less than this spell's converted mana cost.
        List<Card> partsToCast = new ArrayList<>();
        if (cardToCast != null) {
            if (cardToCast instanceof SplitCard) {
                partsToCast.add(((SplitCard) cardToCast).getLeftHalfCard());
                partsToCast.add(((SplitCard) cardToCast).getRightHalfCard());
                partsToCast.add(cardToCast);
            } else if (cardToCast instanceof AdventureCard) {
                partsToCast.add(((AdventureCard) cardToCast).getSpellCard());
                partsToCast.add(cardToCast);
            } else if (cardToCast instanceof ModalDoubleFacesCard) {
                partsToCast.add(((ModalDoubleFacesCard) cardToCast).getLeftHalfCard());
                partsToCast.add(((ModalDoubleFacesCard) cardToCast).getRightHalfCard());
            } else {
                partsToCast.add(cardToCast);
            }
            // remove too big cmc
            partsToCast.removeIf(card -> card.getManaValue() >= sourceCost);
            // remove non spells
            partsToCast.removeIf(card -> card.getSpellAbility() == null);
        }

        String partsInfo = partsToCast.stream()
                .map(MageObject::getIdName)
                .collect(Collectors.joining(" or "));
        if (cardToCast != null
                && partsToCast.size() > 0
                && controller.chooseUse(outcome, "Cast spell without paying its mana cost (" + partsInfo + ")?", source, game)) {
            try {
                // enable free cast for all compatible parts
                partsToCast.forEach(card -> game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE));
                controller.cast(controller.chooseAbilityForCast(cardToCast, game, true),
                        game, true, new ApprovingObject(source, game));
            } finally {
                partsToCast.forEach(card -> game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null));
            }
        }

        // Then put all cards exiled this way that weren't cast on the bottom of your library in a random order.
        cardsToExile.removeIf(uuid -> game.getState().getZone(uuid) != Zone.EXILED);
        return controller.putCardsOnBottomOfLibrary(cardsToExile, game, source, false);
    }

    @Override
    public CascadeEffect copy() {
        return new CascadeEffect(this);
    }
}

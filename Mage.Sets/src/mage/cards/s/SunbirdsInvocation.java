package mage.cards.s;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class SunbirdsInvocation extends CardImpl {

    public SunbirdsInvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");

        // Whenever you cast a spell from your hand, reveal the top X cards of your library, 
        // where X is that spell's converted mana cost. You may cast a card revealed this 
        // way with converted mana cost X or less without paying its mana cost. Put the 
        // rest on the bottom of your library in a random order.
        this.addAbility(new SunbirdsInvocationTriggeredAbility());
    }

    private SunbirdsInvocation(final SunbirdsInvocation card) {
        super(card);
    }

    @Override
    public SunbirdsInvocation copy() {
        return new SunbirdsInvocation(this);
    }
}

class SunbirdsInvocationTriggeredAbility extends SpellCastControllerTriggeredAbility {

    public SunbirdsInvocationTriggeredAbility() {
        super(new SunbirdsInvocationEffect(), false);
    }

    public SunbirdsInvocationTriggeredAbility(SunbirdsInvocationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null
                    && spell.getFromZone() == Zone.HAND
                    && spell.isOwnedBy(getControllerId())) { // must be from the controller's hand
                if (spell.getCard() != null) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(spell.getId()));
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public SunbirdsInvocationTriggeredAbility copy() {
        return new SunbirdsInvocationTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you cast a spell from your hand, "
                + "reveal the top X cards of your library, "
                + "where X is that spell's mana value. "
                + "You may cast a card revealed this way with "
                + "mana value X or less without paying its mana cost."
                + " Put the rest on the bottom of your library in a random order.";
    }
}

class SunbirdsInvocationEffect extends OneShotEffect {

    public SunbirdsInvocationEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "reveal the top X cards of your library, where X is that "
                + "spell's mana value. You may cast a card revealed this "
                + "way with mana value X or less without paying its mana cost. "
                + "Put the rest on the bottom of your library in a random order";
    }

    public SunbirdsInvocationEffect(final SunbirdsInvocationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null
                || sourceObject == null) {
            return false;
        }
        Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        int xValue = spell.getManaValue();
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, xValue));
        if (!cards.isEmpty()) {
            controller.revealCards(sourceObject.getIdName(), cards, game);

            FilterCard filter = new FilterNonlandCard("card revealed this way with mana value " + xValue + " or less");
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
            TargetCard target = new TargetCard(1, Zone.LIBRARY, filter);

            if (controller.chooseTarget(Outcome.PlayForFree, cards, target, source, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    if (controller.chooseUse(Outcome.Benefit, "Cast " + card.getLogName() + " without paying its mana cost?", source, game)) {
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                        Boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(card, game, true),
                                game, true, new ApprovingObject(source, game));
                        game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                        if (cardWasCast) {
                            cards.remove(card);
                        }
                    }
                }
            }
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }

    @Override
    public SunbirdsInvocationEffect copy() {
        return new SunbirdsInvocationEffect(this);
    }
}

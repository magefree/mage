package mage.cards.n;

import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author rjayz
 */
public final class NeeraWildMage extends CardImpl {

    public NeeraWildMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(7);

        // Whenever you cast a spell, you may put it on the bottom of its owner's library.
        // If you do, reveal cards from the top of your library until you reveal a nonland card.
        // You may cast that card without paying its mana cost.
        // Then put the rest on the bottom of your library in a random order.
        // This ability triggers only once each turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new NeeraWildMageEffect(), new FilterSpell(),  true, true).setTriggersOnce(true));
    }

    private NeeraWildMage(final NeeraWildMage card) {
        super(card);
    }

    @Override
    public NeeraWildMage copy() {
        return new NeeraWildMage(this);
    }
}

class NeeraWildMageEffect extends OneShotEffect {

    public NeeraWildMageEffect() {
        super(Outcome.Neutral);
        staticText = "you may put it on the bottom of its owner's library. If you do, reveal cards from the top of your library until you reveal a nonland card. You may cast that card without paying its mana cost. Then put the rest on the bottom of your library in a random order. This ability triggers only once each turn.";
    }

    public NeeraWildMageEffect(final NeeraWildMageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        boolean noLongerOnStack = false; // Spell needs to be on the stack to put it on the bottom of your library
        if (spell == null) {
            spell = ((Spell) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK));
            noLongerOnStack = true;
        }
        if (spell == null) {
            return false;
        }

        Player spellController = game.getPlayer(spell.getControllerId());
        if (spellController == null) {
            return false;
        }

        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }

        if (!noLongerOnStack) {
            spellController.putCardsOnBottomOfLibrary(spell, game, source, true);
        }

        if (!spellController.getLibrary().hasCards()) {
            return true;
        }

        boolean cardWasCast = false;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.getLibrary().hasCards()) {
            CardsImpl toReveal = new CardsImpl();
            Card eligibleCard = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                toReveal.add(card);
                if (!card.isLand(game)) {
                    eligibleCard = card;
                    break;
                }
            }
            controller.revealCards(source, toReveal, game);
            if (eligibleCard != null
                    && controller.chooseUse(Outcome.PlayForFree, "Cast " + eligibleCard.getLogName() + " without paying its mana cost?", source, game)) {
                game.getState().setValue("PlayFromNotOwnHandZone" + eligibleCard.getId(), Boolean.TRUE);
                cardWasCast = controller.cast(controller.chooseAbilityForCast(eligibleCard, game, true),
                        game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + eligibleCard.getId(), null);
                if (cardWasCast) {
                    toReveal.remove(eligibleCard);
                }
            }
            controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        }
        return cardWasCast;
    }

    @Override
    public NeeraWildMageEffect copy() {
        return new NeeraWildMageEffect(this);
    }

}

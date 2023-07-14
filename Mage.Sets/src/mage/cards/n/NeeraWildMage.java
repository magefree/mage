package mage.cards.n;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
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

        this.supertype.add(SuperType.LEGENDARY);
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
        this.addAbility(new SpellCastControllerTriggeredAbility(new NeeraWildMageEffect(), StaticFilters.FILTER_SPELL_A,
                true, true).setTriggersOnceEachTurn(true));
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
        if (spell == null) {
            return false;
        }

        Player spellController = game.getPlayer(spell.getControllerId());
        if (spellController == null) {
            return false;
        }

        if(!spellController.putCardsOnBottomOfLibrary(spell, game, source, true)) {
            return false;
        }

        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || controller.getLibrary() == null || !controller.getLibrary().hasCards()) {
            return false;
        }

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
            boolean cardWasCast = controller.cast(controller.chooseAbilityForCast(eligibleCard, game, true),
                    game, true, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + eligibleCard.getId(), null);
            if (cardWasCast) {
                toReveal.remove(eligibleCard);
            }
        }

        controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);

        return true;
    }

    @Override
    public NeeraWildMageEffect copy() {
        return new NeeraWildMageEffect(this);
    }

}

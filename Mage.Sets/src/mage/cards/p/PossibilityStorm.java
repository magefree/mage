package mage.cards.p;

import mage.ApprovingObject;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Library;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PossibilityStorm extends CardImpl {

    public PossibilityStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // Whenever a player casts a spell from their hand, that player exiles it, then exiles cards from
        // the top of their library until they exile a card that shares a card type with it. That
        // player may cast that card without paying its mana cost. Then they put all cards exiled with
        // Possibility Storm on the bottom of their library in a random order.
        this.addAbility(new PossibilityStormTriggeredAbility());
    }

    private PossibilityStorm(final PossibilityStorm card) {
        super(card);
    }

    @Override
    public PossibilityStorm copy() {
        return new PossibilityStorm(this);
    }
}

class PossibilityStormTriggeredAbility extends TriggeredAbilityImpl {

    public PossibilityStormTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PossibilityStormEffect(), false);
    }

    public PossibilityStormTriggeredAbility(final PossibilityStormTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PossibilityStormTriggeredAbility copy() {
        return new PossibilityStormTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getZone() != Zone.HAND) { return false; }

        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) { return false; }

        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
        }
        return true;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a player casts a spell from their hand, " ;
    }
}

class PossibilityStormEffect extends OneShotEffect {

    public PossibilityStormEffect() {
        super(Outcome.Neutral);
        staticText = "that player exiles it, then exiles cards from the top of their library until they exile a card that shares a card type with it. That player may cast that card without paying its mana cost. Then they put all cards exiled with {this} on the bottom of their library in a random order";
    }

    public PossibilityStormEffect(final PossibilityStormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        boolean noLongerOnStack = false; // spell was exiled already by another effect, for example NivMagus Elemental
        if (spell == null) {
            spell = ((Spell) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK));
            noLongerOnStack = true;
        }
        if (spell == null) { return false; }

        Player spellController = game.getPlayer(spell.getControllerId());
        if (spellController == null) { return false; }

        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) { return false; }

        if (!noLongerOnStack) {
            spellController.moveCardsToExile(spell, source, game, true, source.getSourceId(), sourceObject.getIdName());
        }

        if (!spellController.getLibrary().hasCards()) { return true; }
        Library library = spellController.getLibrary();
        Card card;
        do {
            card = library.getFromTop(game);
            if (card != null) {
                spellController.moveCardsToExile(card, source, game, true, source.getSourceId(), sourceObject.getIdName());
            }
        } while (library.hasCards() && card != null && !sharesType(card, spell.getCardType(game), game));

        if (card != null && sharesType(card, spell.getCardType(game), game)
                && !card.isLand(game)
                && card.getSpellAbility().canChooseTarget(game, spellController.getId())) {
            if (spellController.chooseUse(Outcome.PlayForFree, "Cast " + card.getLogName() + " without paying cost?", source, game)) {
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                spellController.cast(spellController.chooseAbilityForCast(card, game, true), game, true, new ApprovingObject(source, game));
                game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
            }
        }

        ExileZone exile = game.getExile().getExileZone(source.getSourceId());
        if (exile != null) {
            spellController.putCardsOnBottomOfLibrary(exile, game, source, false);
        }

        return true;
    }

    private boolean sharesType(Card card, List<CardType> cardTypes, Game game) {
        for (CardType type : card.getCardType(game)) {
            if (cardTypes.contains(type)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public PossibilityStormEffect copy() {
        return new PossibilityStormEffect(this);
    }

}

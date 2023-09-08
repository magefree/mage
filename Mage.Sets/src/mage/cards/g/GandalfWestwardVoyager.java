package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.*;

/**
 * @author Susucr
 */
public final class GandalfWestwardVoyager extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with mana value 5 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_GREATER, 5));
    }

    public GandalfWestwardVoyager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever you cast a spell with mana value 5 or greater, each opponent reveals the top card of their library. If any of those cards shares a card type with that spell, copy that spell, you may choose new targets for the copy, and each opponent draws a card. Otherwise, you draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new GandalfWestwardVoyagerEffect(),
                filter, false, SetTargetPointer.SPELL
        ));
    }

    private GandalfWestwardVoyager(final GandalfWestwardVoyager card) {
        super(card);
    }

    @Override
    public GandalfWestwardVoyager copy() {
        return new GandalfWestwardVoyager(this);
    }
}

class GandalfWestwardVoyagerEffect extends OneShotEffect {

    GandalfWestwardVoyagerEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent reveals the top card of their library. If any of those cards "
                + "shares a card type with that spell, copy that spell, you may choose new targets for "
                + "the copy, and each opponent draws a card. Otherwise, you draw a card";
    }

    private GandalfWestwardVoyagerEffect(final GandalfWestwardVoyagerEffect effect) {
        super(effect);
    }

    @Override
    public GandalfWestwardVoyagerEffect copy() {
        return new GandalfWestwardVoyagerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(targetPointer.getFirst(game, source));
        List<CardType> typesSpell = spell == null ? new ArrayList<>() : spell.getCardType(game);

        boolean foundCard = false;
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            Card card = player.getLibrary().getFromTop(game);
            if (card == null) {
                continue;
            }

            // each opponent reveals the top card of their library.
            player.revealCards(source, " — " + player.getName(), new CardsImpl(card), game, true);

            Set<CardType> types = new HashSet<>(card.getCardType(game));
            types.retainAll(typesSpell);
            foundCard |= !types.isEmpty();
        }

        if (foundCard) {
            // If any of those cards shares a card type with that spell,
            // copy that spell, you may choose new targets for the copy,
            if (spell != null) {
                spell.createCopyOnStack(game, source, source.getControllerId(), true);
            }

            // and each opponent draws a card.
            new DrawCardAllEffect(1, TargetController.OPPONENT).apply(game, source);
        } else {
            // Otherwise, you draw a card
            new DrawCardSourceControllerEffect(1).apply(game, source);
        }
        return true;
    }
}

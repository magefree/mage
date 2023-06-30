package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Grath
 */
public final class KenessosPriestOfThassa extends CardImpl {

    public KenessosPriestOfThassa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // If you would scry a number of cards, scry that many cards plus one instead.
        this.addAbility(new SimpleStaticAbility(new KenessosPriestOfThassaReplacementEffect()));

        // {3}{G/U}: Look at the top card of your library. If it’s a Kraken, Leviathan, Octopus, or Serpent creature card,
        // you may put it onto the battlefield. If you don’t put the card onto the battlefield, you may put it on the bottom of your library.
        this.addAbility(new SimpleActivatedAbility(
                new KenessosPriestOfThassaActivatedEffect(),
                new ManaCostsImpl<>("{3}{G/U}")
        ));
    }

    private KenessosPriestOfThassa(final KenessosPriestOfThassa card) {
        super(card);
    }

    @Override
    public KenessosPriestOfThassa copy() {
        return new KenessosPriestOfThassa(this);
    }
}

class KenessosPriestOfThassaReplacementEffect extends ReplacementEffectImpl {

    KenessosPriestOfThassaReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would scry a number of cards, scry that many cards plus one instead.";
    }

    private KenessosPriestOfThassaReplacementEffect(final KenessosPriestOfThassaReplacementEffect effect) {
        super(effect);
    }

    @Override
    public KenessosPriestOfThassaReplacementEffect copy() {
        return new KenessosPriestOfThassaReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SCRY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}

class KenessosPriestOfThassaActivatedEffect extends OneShotEffect {

    private static final FilterCard filter
            = new FilterCard("a Kraken, Leviathan, Octopus, or Serpent creature card");

    static {
        filter.add(Predicates.or(
                SubType.KRAKEN.getPredicate(),
                SubType.LEVIATHAN.getPredicate(),
                SubType.OCTOPUS.getPredicate(),
                SubType.SERPENT.getPredicate()
        ));
    }
    public KenessosPriestOfThassaActivatedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Look at the top card of your library. If it's a Kraken, Leviathan, Octopus, or Serpent " +
                "creature card, you may put it onto the battlefield. If you don't put the card onto the battlefield, " +
                "you may put it on the bottom of your library.";
    }

    public KenessosPriestOfThassaActivatedEffect(final KenessosPriestOfThassaActivatedEffect effect) {
        super(effect);
    }

    @Override
    public KenessosPriestOfThassaActivatedEffect copy() {
        return new KenessosPriestOfThassaActivatedEffect(this);
    }
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards(null, card, game);
        if (filter.match(card, game) && player.chooseUse(outcome, "Put " + card.getName() + " onto the battlefield?", source, game)) {
            return player.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        if (player.chooseUse(outcome, "Put " + card.getName() + " on the bottom of your library?", source, game)) {
            return player.putCardsOnBottomOfLibrary(card, game, source, true);
        }
        return true;
    }
}
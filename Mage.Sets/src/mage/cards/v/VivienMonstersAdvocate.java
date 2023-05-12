package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.effects.common.continuous.PlayTheTopCardEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BeastToken;
import mage.game.permanent.token.Token;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class VivienMonstersAdvocate extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("cast creature spells");

    public VivienMonstersAdvocate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIVIEN);
        this.setStartingLoyalty(3);

        // You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast creature spells from the top of your library.
        this.addAbility(new SimpleStaticAbility(new PlayTheTopCardEffect(TargetController.YOU, filter, false)));

        // +1: Create a 3/3 green Beast creature token. Put your choice of a vigilance 
        // counter, a reach counter, or a trample counter on it.
        this.addAbility(new LoyaltyAbility(new VivienMonstersAdvocateTokenEffect(), 1));

        // −2: When you cast your next creature spell this turn, search your library for a 
        // creature card with lesser converted mana cost, put it onto the battlefield, then shuffle your library.
        this.addAbility(new LoyaltyAbility(
                new CreateDelayedTriggeredAbilityEffect(new VivienMonstersAdvocateTriggeredAbility()), -2
        ));
    }

    private VivienMonstersAdvocate(final VivienMonstersAdvocate card) {
        super(card);
    }

    @Override
    public VivienMonstersAdvocate copy() {
        return new VivienMonstersAdvocate(this);
    }
}

class VivienMonstersAdvocateTokenEffect extends OneShotEffect {

    private static final Token token = new BeastToken();
    private static final Set<String> choices = new LinkedHashSet<>(Arrays.asList(
            "Vigilance", "Reach", "Trample"
    ));

    VivienMonstersAdvocateTokenEffect() {
        super(Outcome.Benefit);
        staticText = "Create a 3/3 green Beast creature token. Put your choice of a vigilance counter, "
                + "a reach counter, or a trample counter on it.";
    }

    private VivienMonstersAdvocateTokenEffect(final VivienMonstersAdvocateTokenEffect effect) {
        super(effect);
    }

    @Override
    public VivienMonstersAdvocateTokenEffect copy() {
        return new VivienMonstersAdvocateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent == null) {
                continue;
            }
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose vigilance, reach, or trample counter");
            choice.setChoices(choices);
            player.choose(outcome, choice, game);
            String chosen = choice.getChoice();
            if (chosen != null) {
                permanent.addCounters(CounterType.findByName(chosen.toLowerCase(
                        Locale.ENGLISH)).createInstance(), source.getControllerId(), source, game);
            }
        }
        return true;
    }
}

class VivienMonstersAdvocateTriggeredAbility extends DelayedTriggeredAbility {

    VivienMonstersAdvocateTriggeredAbility() {
        super(null, Duration.EndOfTurn, true, false);
    }

    private VivienMonstersAdvocateTriggeredAbility(final VivienMonstersAdvocateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        if (spell != null
                && spell.isCreature(game)) {
            int cmc = spell.getManaValue();
            FilterCard filter = new FilterCreatureCard("creature card with mana value less than " + cmc);
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, cmc));
            this.getEffects().clear();
            this.getEffects().add(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)));
            return true;
        }
        return false;
    }

    @Override
    public DelayedTriggeredAbility copy() {
        return new VivienMonstersAdvocateTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you cast your next creature spell this turn, "
                + "search your library for a creature card with lesser mana value, "
                + "put it onto the battlefield, then shuffle.";
    }
}

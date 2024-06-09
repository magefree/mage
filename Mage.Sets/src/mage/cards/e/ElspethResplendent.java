package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.*;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Angel33Token;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.*;

/**
 * @author TheElk801
 */
public final class ElspethResplendent extends CardImpl {

    public ElspethResplendent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELSPETH);
        this.setStartingLoyalty(5);

        // +1: Choose up to one target creature. Put a +1/+1 counter and a counter from among flying, first strike, lifelink, or vigilance on it.
        Ability ability = new LoyaltyAbility(new ElspethResplendentCounterEffect(), 1);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // −3: Look at the top seven cards of your library. You may put a permanent card with mana value 3 or less from among them onto the battlefield with a shield counter on it. Put the rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new ElspethResplendentLookEffect(), -3));

        // −7: Create five 3/3 white Angel creature tokens with flying.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new Angel33Token(), 5), -7));
    }

    private ElspethResplendent(final ElspethResplendent card) {
        super(card);
    }

    @Override
    public ElspethResplendent copy() {
        return new ElspethResplendent(this);
    }
}

class ElspethResplendentCounterEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>();

    static {
        choices.add("Flying");
        choices.add("First strike");
        choices.add("Lifelink");
        choices.add("Vigilance");
    }

    ElspethResplendentCounterEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to one target creature. Put a +1/+1 counter and a counter " +
                "from among flying, first strike, lifelink, or vigilance on it";
    }

    private ElspethResplendentCounterEffect(final ElspethResplendentCounterEffect effect) {
        super(effect);
    }

    @Override
    public ElspethResplendentCounterEffect copy() {
        return new ElspethResplendentCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose an ability counter to add");
        choice.setChoices(choices);
        player.choose(outcome, choice, game);
        CounterType counterType = CounterType.findByName(choice.getChoice().toLowerCase(Locale.ROOT));
        permanent.addCounters(counterType.createInstance(), source, game);
        return true;
    }
}

class ElspethResplendentLookEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterPermanentCard("permanent card with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    ElspethResplendentLookEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top seven cards of your library. You may put a permanent card " +
                "with mana value 3 or less from among them onto the battlefield with a shield counter on it. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private ElspethResplendentLookEffect(final ElspethResplendentLookEffect effect) {
        super(effect);
    }

    @Override
    public ElspethResplendentLookEffect copy() {
        return new ElspethResplendentLookEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 7));
        TargetCard target = new TargetCardInLibrary(0, 1, filter);
        player.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game);
            Permanent permanent = game.getPermanent(card.getId());
            if (permanent != null) {
                permanent.addCounters(CounterType.SHIELD.createInstance(), source, game);
            }
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

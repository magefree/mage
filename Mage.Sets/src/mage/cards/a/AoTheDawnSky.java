package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AoTheDawnSky extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("permanent you control that's a creature or Vehicle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public AoTheDawnSky(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Ao, the Dawn Sky dies, choose one —
        // • Look at the top seven cards of your library. Put any number of nonland permanent cards with total mana value 4 or less from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        Ability ability = new DiesSourceTriggeredAbility(new AoTheDawnSkyEffect());

        // • Put two +1/+1 counters on each permanent you control that's a creature or Vehicle.
        ability.addMode(new Mode(new AddCountersAllEffect(CounterType.P1P1.createInstance(2), filter)));
        this.addAbility(ability);
    }

    private AoTheDawnSky(final AoTheDawnSky card) {
        super(card);
    }

    @Override
    public AoTheDawnSky copy() {
        return new AoTheDawnSky(this);
    }
}

class AoTheDawnSkyEffect extends OneShotEffect {

    AoTheDawnSkyEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top seven cards of your library. Put any number of nonland permanent cards " +
                "with total mana value 4 or less from among them onto the battlefield. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private AoTheDawnSkyEffect(final AoTheDawnSkyEffect effect) {
        super(effect);
    }

    @Override
    public AoTheDawnSkyEffect copy() {
        return new AoTheDawnSkyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 7));
        TargetCard target = new AoTheDawnSkyTarget();
        player.choose(outcome, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

class AoTheDawnSkyTarget extends TargetCardInLibrary {

    private static final FilterCard filter = new FilterPermanentCard("nonland permanent card");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    AoTheDawnSkyTarget() {
        super(0, Integer.MAX_VALUE, filter);
    }

    private AoTheDawnSkyTarget(final AoTheDawnSkyTarget target) {
        super(target);
    }

    @Override
    public AoTheDawnSkyTarget copy() {
        return new AoTheDawnSkyTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null
                && card.getManaValue()
                + this
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum() <= 4;
    }
}

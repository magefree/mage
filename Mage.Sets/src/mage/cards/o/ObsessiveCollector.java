package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObsessiveCollector extends CardImpl {

    public ObsessiveCollector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Whenever Obsessive Collector deals combat damage to a player, seek a card with mana value equal to the number of cards in your hand.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ObsessiveCollectorEffect(), false));
    }

    private ObsessiveCollector(final ObsessiveCollector card) {
        super(card);
    }

    @Override
    public ObsessiveCollector copy() {
        return new ObsessiveCollector(this);
    }
}

enum ObsessiveCollectorPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return game.getPlayer(input.getPlayerId()).getHand().size() == input.getObject().getManaValue();
    }
}

class ObsessiveCollectorEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(ObsessiveCollectorPredicate.instance);
    }

    ObsessiveCollectorEffect() {
        super(Outcome.Benefit);
        staticText = "seek a card with mana value equal to the number of cards in your hand";
    }

    private ObsessiveCollectorEffect(final ObsessiveCollectorEffect effect) {
        super(effect);
    }

    @Override
    public ObsessiveCollectorEffect copy() {
        return new ObsessiveCollectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.seekCard(filter, source, game);
    }
}

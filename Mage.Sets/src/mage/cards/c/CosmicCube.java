package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author nandmp
 */
public final class CosmicCube extends CardImpl {

    public CosmicCube(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Whenever you attack, look at the top six cards of your library. You may cast a spell from among them with mana value less than or equal to the greatest power among attacking creatures you control without paying its mana cost. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new CosmicCubeEffect(), 1));
    }

    private CosmicCube(final CosmicCube card) {
        super(card);
    }

    @Override
    public CosmicCube copy() {
        return new CosmicCube(this);
    }
}

class CosmicCubeEffect extends OneShotEffect {

    CosmicCubeEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top six cards of your library. You may cast a spell from among them "
                + "with mana value less than or equal to the greatest power among attacking creatures "
                + "you control without paying its mana cost. Put the rest on the bottom of your library "
                + "in a random order";
    }

    private CosmicCubeEffect(final CosmicCubeEffect effect) {
        super(effect);
    }

    @Override
    public CosmicCubeEffect copy() {
        return new CosmicCubeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int greatestPower = game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURES,
                        source.getControllerId(), source, game
                )
                .stream()
                .filter(permanent -> permanent.isAttacking())
                .mapToInt(permanent -> permanent.getPower().getValue())
                .max()
                .orElse(0);
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        FilterCard filter = new FilterCard();
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, greatestPower));
        CardUtil.castSpellWithAttributesForFree(player, source, game, cards, filter);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

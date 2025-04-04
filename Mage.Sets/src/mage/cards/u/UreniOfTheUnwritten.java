package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author ilyagart
 */
public final class UreniOfTheUnwritten extends CardImpl {

    public UreniOfTheUnwritten(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Ureni enters or attacks, look at the top eight cards of your library. You may put a Dragon creature card from among them onto the battlefield. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new UreniOfTheUnwrittenEffect(), false));
    }

    private UreniOfTheUnwritten(final UreniOfTheUnwritten card) {
        super(card);
    }

    @Override
    public UreniOfTheUnwritten copy() {
        return new UreniOfTheUnwritten(this);
    }
}

class UreniOfTheUnwrittenEffect extends OneShotEffect {

    UreniOfTheUnwrittenEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top eight cards of your library. You may put a Dragon creature card from among them onto the battlefield. Put the rest on the bottom of your library in a random order.";
    }

    private UreniOfTheUnwrittenEffect(final mage.cards.u.UreniOfTheUnwrittenEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.u.UreniOfTheUnwrittenEffect copy() {
        return new mage.cards.u.UreniOfTheUnwrittenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 8));
        if (!cards.isEmpty()) {
            FilterCreatureCard filter = new FilterCreatureCard("Dragon creature cards");
            filter.add(SubType.DRAGON.getPredicate());
            TargetCard targetCard = new TargetCard(0, 1, Zone.LIBRARY, filter);
            targetCard.withNotTarget(true);
            controller.choose(Outcome.PutCreatureInPlay, cards, targetCard, source, game);
            controller.moveCards(game.getCard(targetCard.getFirstTarget()), Zone.BATTLEFIELD, source, game);
            cards.retainZone(Zone.LIBRARY, game);
            controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }
}
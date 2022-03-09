
package mage.cards.d;

import mage.ApprovingObject;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class DjinnOfWishes extends CardImpl {

    public DjinnOfWishes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.DJINN);

        this.color.setBlue(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        // Djinn of Wishes enters the battlefield with three wish counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.WISH.createInstance(3)), "with three wish counters on it"));

        // {2}{U}{U}, Remove a wish counter from Djinn of Wishes: Reveal the top card of your library. You may play that card without paying its mana cost. If you don't, exile it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DjinnOfWishesEffect(), new ManaCostsImpl("{2}{U}{U}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.WISH.createInstance()));
        this.addAbility(ability);
    }

    private DjinnOfWishes(final DjinnOfWishes card) {
        super(card);
    }

    @Override
    public DjinnOfWishes copy() {
        return new DjinnOfWishes(this);
    }
}

class DjinnOfWishesEffect extends OneShotEffect {

    public DjinnOfWishesEffect() {
        super(Outcome.PlayForFree);
        staticText = "Reveal the top card of your library. You may play that card without paying its mana cost. If you don't, exile it";
    }

    public DjinnOfWishesEffect(final DjinnOfWishesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null && controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            controller.revealCards(sourceObject.getIdName(), cards, game);
            if (!controller.chooseUse(Outcome.PlayForFree, "Play " + card.getName() + " without paying its mana cost?", source, game)
                    || !controller.playCard(card, game, true, new ApprovingObject(source, game))) {
                controller.moveCards(card, Zone.EXILED, source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public DjinnOfWishesEffect copy() {
        return new DjinnOfWishesEffect(this);
    }

}

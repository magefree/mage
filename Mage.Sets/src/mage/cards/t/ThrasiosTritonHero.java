package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ThrasiosTritonHero extends CardImpl {

    public ThrasiosTritonHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {4}: Scry 1, then reveal the top card of your library. If it's a land card, put it onto the battlefield tapped. Otherwise, draw a card.
        this.addAbility(new SimpleActivatedAbility(new ThrasiosTritonHeroEffect(), new GenericManaCost(4)));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private ThrasiosTritonHero(final ThrasiosTritonHero card) {
        super(card);
    }

    @Override
    public ThrasiosTritonHero copy() {
        return new ThrasiosTritonHero(this);
    }
}

class ThrasiosTritonHeroEffect extends OneShotEffect {

    public ThrasiosTritonHeroEffect() {
        super(Outcome.DrawCard);
        this.staticText = "scry 1, then reveal the top card of your library. " +
                "If it's a land card, put it onto the battlefield tapped. Otherwise, draw a card";
    }

    public ThrasiosTritonHeroEffect(final ThrasiosTritonHeroEffect effect) {
        super(effect);
    }

    @Override
    public ThrasiosTritonHeroEffect copy() {
        return new ThrasiosTritonHeroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.scry(1, source, game);
        if (!controller.getLibrary().hasCards()) {
            return true;
        }
        CardsImpl cards = new CardsImpl();
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        cards.add(card);
        controller.revealCards(source, cards, game);
        if (card.isLand(game)) {
            return controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        controller.drawCards(1, source, game);
        return true;
    }
}

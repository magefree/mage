
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ThrasiosTritonHero extends CardImpl {

    public ThrasiosTritonHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{U}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {4}: Scry 1, then reveal the top card of your library. If it's a land card, put it onto the battlefield tapped. Otherwise, draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1), new GenericManaCost(4));
        ability.addEffect(new ThrasiosTritonHeroEffect());
        this.addAbility(ability);
        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    public ThrasiosTritonHero(final ThrasiosTritonHero card) {
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
        this.staticText = "then reveal the top card of your library. If it's a land card, put it onto the battlefield tapped. Otherwise, draw a card";
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
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject == null || controller == null) {
            return false;
        }
        if (controller.getLibrary().hasCards()) {
            CardsImpl cards = new CardsImpl();
            Card card = controller.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            cards.add(card);
            controller.revealCards(sourceObject.getName(), cards, game);
            if (card.isLand()) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
            } else {
                controller.drawCards(1, game);
            }
        }
        return true;
    }
}

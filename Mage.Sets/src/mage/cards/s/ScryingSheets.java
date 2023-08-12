
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class ScryingSheets extends CardImpl {

    public ScryingSheets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.SNOW);

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}{S}, {T}: Look at the top card of your library. If that card is snow, you may reveal it and put it into your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryingSheetsEffect(), new ManaCostsImpl<>("{1}{S}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ScryingSheets(final ScryingSheets card) {
        super(card);
    }

    @Override
    public ScryingSheets copy() {
        return new ScryingSheets(this);
    }
}

class ScryingSheetsEffect extends OneShotEffect {

    ScryingSheetsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top card of your library. If that card is snow, you may reveal it and put it into your hand";
    }

    ScryingSheetsEffect(final ScryingSheetsEffect effect) {
        super(effect);
    }

    @Override
    public ScryingSheetsEffect copy() {
        return new ScryingSheetsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                CardsImpl cards = new CardsImpl();
                cards.add(card);
                controller.lookAtCards(sourceObject.getIdName(), cards, game);
                if (card.isSnow(game)) {
                    if (controller.chooseUse(outcome, "Reveal " + card.getLogName() + " and put it into your hand?", source, game)) {
                        controller.moveCards(card, Zone.HAND, source, game);
                        controller.revealCards(sourceObject.getIdName(), cards, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}


package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class Zoologist extends CardImpl {

    public Zoologist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN, SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {3}{G}, {tap}: Reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, put it into your graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ZoologistEffect(), new ManaCostsImpl<>("{3}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private Zoologist(final Zoologist card) {
        super(card);
    }

    @Override
    public Zoologist copy() {
        return new Zoologist(this);
    }
}

class ZoologistEffect extends OneShotEffect {

    public ZoologistEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Reveal the top card of your library. If it's a creature card, put it onto the battlefield. Otherwise, put it into your graveyard";
    }

    public ZoologistEffect(final ZoologistEffect effect) {
        super(effect);
    }

    @Override
    public ZoologistEffect copy() {
        return new ZoologistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
            if (card != null) {
                if (card.isCreature(game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                } else {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
            }
        }
        return true;
    }
}

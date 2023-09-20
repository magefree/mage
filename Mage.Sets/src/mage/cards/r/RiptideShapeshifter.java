
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class RiptideShapeshifter extends CardImpl {

    public RiptideShapeshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{U}{U}, Sacrifice Riptide Shapeshifter: Choose a creature type. Reveal cards from the top of your library until you reveal a creature card of that type. Put that card onto the battlefield and shuffle the rest into your library.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RiptideShapeshifterEffect(), new ManaCostsImpl<>("{2}{U}{U}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private RiptideShapeshifter(final RiptideShapeshifter card) {
        super(card);
    }

    @Override
    public RiptideShapeshifter copy() {
        return new RiptideShapeshifter(this);
    }
}

class RiptideShapeshifterEffect extends OneShotEffect {

    RiptideShapeshifterEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose a creature type. Reveal cards from the top of your library until you reveal a creature card of that type. Put that card onto the battlefield and shuffle the rest into your library";
    }

    private RiptideShapeshifterEffect(final RiptideShapeshifterEffect effect) {
        super(effect);
    }

    @Override
    public RiptideShapeshifterEffect copy() {
        return new RiptideShapeshifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Choice choice = new ChoiceCreatureType(sourceObject);
            if (!controller.choose(Outcome.BoostCreature, choice, game)) {
                return false;
            }
            Cards revealedCards = new CardsImpl();
            for (Card card : controller.getLibrary().getCards(game)) {
                if (card.isCreature(game) && card.hasSubtype(SubType.byDescription(choice.getChoice()), game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    break;
                }
                revealedCards.add(card);
            }
            controller.revealCards(sourceObject.getIdName(), revealedCards, game);
            controller.moveCards(revealedCards, Zone.LIBRARY, source, game);
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}

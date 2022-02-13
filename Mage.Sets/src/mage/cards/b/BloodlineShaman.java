
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class BloodlineShaman extends CardImpl {

    public BloodlineShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF, SubType.WIZARD, SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Choose a creature type. Reveal the top card of your library. If that card is a creature card of the chosen type, put it into your hand.
        // Otherwise, put it into your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BloodlineShamanEffect(), new TapSourceCost()));
    }

    private BloodlineShaman(final BloodlineShaman card) {
        super(card);
    }

    @Override
    public BloodlineShaman copy() {
        return new BloodlineShaman(this);
    }
}

class BloodlineShamanEffect extends OneShotEffect {

    public BloodlineShamanEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose a creature type. Reveal the top card of your library. If that card is a creature card of the chosen type, put it into your hand. "
                + "Otherwise, put it into your graveyard";
    }

    public BloodlineShamanEffect(final BloodlineShamanEffect effect) {
        super(effect);
    }

    @Override
    public BloodlineShamanEffect copy() {
        return new BloodlineShamanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject == null) { return false; }

        Choice typeChoice = new ChoiceCreatureType(sourceObject);
        if (!controller.choose(outcome, typeChoice, game)) { return false; }

        game.informPlayers(sourceObject.getLogName() + " chosen type: " + typeChoice.getChoice());
        FilterCard filterSubtype = new FilterCard();
        filterSubtype.add(SubType.byDescription(typeChoice.getChoice()).getPredicate());

        // Reveal the top card of your library.
        if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            controller.revealCards(sourceObject.getIdName(), cards, game);

            if (card != null) {
                // If that card is a creature card of the chosen type, put it into your hand.
                if (filterSubtype.match(card, game)) {
                    controller.moveCards(card, Zone.HAND, source, game);
                    // Otherwise, put it into your graveyard.
                } else {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
            }
        }
        return true;
    }
}

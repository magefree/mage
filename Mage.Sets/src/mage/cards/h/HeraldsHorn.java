
package mage.cards.h;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionAllOfChosenSubtypeEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Saga
 */
public final class HeraldsHorn extends CardImpl {

    public HeraldsHorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // As Herald's Horn enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.BoostCreature)));

        // Creature spells you cast of the chosen type cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new SpellsCostReductionAllOfChosenSubtypeEffect(new FilterCreatureCard("Creature spells you cast of the chosen type"), 1, true)));

        // At the beginning of your upkeep, look at the top card of your library. If it's a creature card of the chosen type, you may reveal it and put it into your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new HeraldsHornEffect(), TargetController.YOU, false));
    }

    private HeraldsHorn(final HeraldsHorn card) {
        super(card);
    }

    @Override
    public HeraldsHorn copy() {
        return new HeraldsHorn(this);
    }
}

class HeraldsHornEffect extends OneShotEffect {

    public HeraldsHornEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top card of your library. If it's a creature card of the chosen type, you may reveal it and put it into your hand";
    }

    public HeraldsHornEffect(final HeraldsHornEffect effect) {
        super(effect);
    }

    @Override
    public HeraldsHornEffect copy() {
        return new HeraldsHornEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);

        // Look at the top card of your library.
        if (controller != null && controller.getLibrary().hasCards() && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            Cards cards = new CardsImpl(card);
            controller.lookAtCards(sourceObject.getIdName(), cards, game);

            // If it's a creature card of the chosen type, you may reveal it and put it into your hand.
            FilterCreatureCard filter = new FilterCreatureCard("creature card of the chosen type");
            SubType subtype = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
            if (subtype == null) {
                return true;
            }
            filter.add(subtype.getPredicate());
            String message = "Reveal the top card of your library and put that card into your hand?";
            if (card != null) {
                if (filter.match(card, game) 
                        && controller.chooseUse(Outcome.Benefit, message, source, game)) {
                    controller.moveCards(card, Zone.HAND, source, game);
                    controller.revealCards(sourceObject.getIdName() + " put into hand", cards, game);
                    return true;
                }
            }
        }
        return false;
    }
}

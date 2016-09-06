/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.planeshift;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author nick.myers
 */
public class SkyshipWeatherlight1 extends CardImpl {

    public SkyshipWeatherlight1(UUID ownerId) {
        super(ownerId, "133a", "Skyship Weatherlight", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "PLS";
        this.supertype.add("Legendary");

        // When Skyship Weatherlight enters the battlefield, search your library for any number of artifact and/or creature cards and exile them. Then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SkyshipWeatherlightEffect(), false));

        // {4}, {tap}, Choose a card at random that was removed from the game with Skyship Weatherlight. Put that card into your hand.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SkyshipWeatherlightEffect2(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public SkyshipWeatherlight1(final SkyshipWeatherlight1 card) {
        super(card);
    }

    @Override
    public SkyshipWeatherlight1 copy() {
        return new SkyshipWeatherlight1(this);
    }

}

class SkyshipWeatherlightEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard("artifact and/or creature card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE)));
    }

    public SkyshipWeatherlightEffect() {

        super(new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), Outcome.Neutral);
        this.staticText = "search your library for any number of artifact and/or creature cards and remove them from the game. Then shuffle your library";

    }

    public SkyshipWeatherlightEffect(final SkyshipWeatherlightEffect effect) {
        super(effect);
    }

    @Override
    public SkyshipWeatherlightEffect copy() {
        return new SkyshipWeatherlightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            if (controller.searchLibrary(target, game)) {
                UUID exileZone = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                if (target.getTargets().size() > 0) {
                    for (UUID cardID : target.getTargets()) {
                        Card card = controller.getLibrary().getCard(cardID, game);
                        if (card != null) {
                            controller.moveCardToExileWithInfo(card, exileZone, sourceObject.getIdName(), source.getSourceId(), game, Zone.LIBRARY, true);
                        }
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

}

class SkyshipWeatherlightEffect2 extends OneShotEffect {

    public SkyshipWeatherlightEffect2() {
        super(Outcome.ReturnToHand);
        this.staticText = "Choose a card at random that was removed from the game with {this}. Put that card into your hand";
    }

    public SkyshipWeatherlightEffect2(final SkyshipWeatherlightEffect2 effect) {
        super(effect);
    }

    @Override
    public SkyshipWeatherlightEffect2 copy() {
        return new SkyshipWeatherlightEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            ExileZone exZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()));
            if (exZone != null) {
                controller.moveCards(exZone.getRandom(game), Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }

}

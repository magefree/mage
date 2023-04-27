package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GolosTirelessPilgrim extends CardImpl {

    public GolosTirelessPilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Golos, Tireless Pilgrim enters the battlefield, you may search your library for a land card, put that card onto the battlefield tapped, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(
                new TargetCardInLibrary(StaticFilters.FILTER_CARD_LAND_A), true
        ), true));

        // {2}{W}{U}{B}{R}{G}: Exile the top three cards of your library. You may play them this turn without paying their mana costs.
        this.addAbility(new SimpleActivatedAbility(
                new GolosTirelessPilgrimEffect(),
                new ManaCostsImpl<>("{2}{W}{U}{B}{R}{G}")
        ));
    }

    private GolosTirelessPilgrim(final GolosTirelessPilgrim card) {
        super(card);
    }

    @Override
    public GolosTirelessPilgrim copy() {
        return new GolosTirelessPilgrim(this);
    }
}

class GolosTirelessPilgrimEffect extends OneShotEffect {

    GolosTirelessPilgrimEffect() {
        super(Outcome.Discard);
        staticText = "Exile the top three cards of your library. "
                + "You may play them this turn without paying their mana costs.";
    }

    private GolosTirelessPilgrimEffect(final GolosTirelessPilgrimEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Card> cards = player.getLibrary().getTopCards(game, 3);
        return PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, cards,
                TargetController.YOU, Duration.EndOfTurn, true, false, false);
    }

    @Override
    public GolosTirelessPilgrimEffect copy() {
        return new GolosTirelessPilgrimEffect(this);
    }
}

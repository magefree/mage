package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TargetPlayerShufflesTargetCardsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInTargetPlayersGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GaeasBlessing extends CardImpl {

    public GaeasBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Target player shuffles up to three target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new TargetPlayerShufflesTargetCardsEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetCardInTargetPlayersGraveyard(3));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));

        // When Gaea's Blessing is put into your graveyard from your library, shuffle your graveyard into your library.
        this.addAbility(new GaeasBlessingTriggeredAbility());
    }

    private GaeasBlessing(final GaeasBlessing card) {
        super(card);
    }

    @Override
    public GaeasBlessing copy() {
        return new GaeasBlessing(this);
    }
}

class GaeasBlessingTriggeredAbility extends ZoneChangeTriggeredAbility {

    GaeasBlessingTriggeredAbility() {
        super(Zone.LIBRARY, Zone.GRAVEYARD, new GaeasBlessingGraveToLibraryEffect(), "", false);
    }

    private GaeasBlessingTriggeredAbility(final GaeasBlessingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GaeasBlessingTriggeredAbility copy() {
        return new GaeasBlessingTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When {this} is put into your graveyard from your library, shuffle your graveyard into your library.";
    }
}

class GaeasBlessingGraveToLibraryEffect extends OneShotEffect {

    GaeasBlessingGraveToLibraryEffect() {
        super(Outcome.GainLife);
        staticText = "shuffle your graveyard into your library";
    }

    private GaeasBlessingGraveToLibraryEffect(final GaeasBlessingGraveToLibraryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.shuffleCardsToLibrary(controller.getGraveyard(), game, source);
        }
        return false;
    }

    @Override
    public GaeasBlessingGraveToLibraryEffect copy() {
        return new GaeasBlessingGraveToLibraryEffect(this);
    }

}

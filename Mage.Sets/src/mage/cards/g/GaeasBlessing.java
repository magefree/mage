
package mage.cards.g;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public final class GaeasBlessing extends CardImpl {

    public GaeasBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        // Target player shuffles up to three target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new GaeasBlessingEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new GaeasBlessingTarget());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        // When Gaea's Blessing is put into your graveyard from your library, shuffle your graveyard into your library.
        this.addAbility(new GaeasBlessingTriggeredAbility());
    }

    public GaeasBlessing(final GaeasBlessing card) {
        super(card);
    }

    @Override
    public GaeasBlessing copy() {
        return new GaeasBlessing(this);
    }
}

class GaeasBlessingEffect extends OneShotEffect {

    public GaeasBlessingEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles up to three target cards from their graveyard into their library";
    }

    public GaeasBlessingEffect(final GaeasBlessingEffect effect) {
        super(effect);
    }

    @Override
    public GaeasBlessingEffect copy() {
        return new GaeasBlessingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            List<UUID> targets = source.getTargets().get(1).getTargets();
            boolean shuffle = false;
            for (UUID targetId : targets) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    if (player.getGraveyard().contains(card.getId())) {
                        player.getGraveyard().remove(card);
                        card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                        shuffle = true;
                    }
                }
            }
            if (shuffle) {
                player.shuffleLibrary(source, game);
            }
            return true;
        }
        return false;
    }
}

class GaeasBlessingTarget extends TargetCardInGraveyard {

    public GaeasBlessingTarget() {
        super(0, 3, new FilterCard());
    }

    public GaeasBlessingTarget(final GaeasBlessingTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            UUID firstTarget = source.getFirstTarget();
            if (firstTarget != null && game.getPlayer(firstTarget).getGraveyard().contains(id)) {
                return filter.match(card, game);
            }
        }
        return false;
    }

    @Override
    public GaeasBlessingTarget copy() {
        return new GaeasBlessingTarget(this);
    }
}

class GaeasBlessingTriggeredAbility extends ZoneChangeTriggeredAbility {

    public GaeasBlessingTriggeredAbility() {
        super(Zone.LIBRARY, Zone.GRAVEYARD, new GaeasBlessingGraveToLibraryEffect(), "", false);
    }

    public GaeasBlessingTriggeredAbility(final GaeasBlessingTriggeredAbility ability) {
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

    public GaeasBlessingGraveToLibraryEffect() {
        super(Outcome.GainLife);
        staticText = "shuffle your graveyard into your library";
    }

    public GaeasBlessingGraveToLibraryEffect(final GaeasBlessingGraveToLibraryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            game.informPlayers(new StringBuilder(controller.getLogName()).append(" shuffle their graveyard into their library").toString());
            for (Card card : controller.getGraveyard().getCards(game)) {
                controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.GRAVEYARD, true, true);
            }
            controller.getLibrary().addAll(controller.getGraveyard().getCards(game), game);
            controller.getGraveyard().clear();
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public GaeasBlessingGraveToLibraryEffect copy() {
        return new GaeasBlessingGraveToLibraryEffect(this);
    }

}

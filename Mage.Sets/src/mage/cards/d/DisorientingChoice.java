package mage.cards.d;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;

/**
 *
 * @author Grath
 */
public final class DisorientingChoice extends CardImpl {

    public DisorientingChoice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}");

        // For each opponent, choose up to one target artifact or enchantment that player controls. For each permanent chosen this way, its controller may exile it. Then if one or more of the chosen permanents are still on the battlefield, you search your library for up to that many land cards, put them onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new DisorientingChoiceEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0,1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
    }

    private DisorientingChoice(final DisorientingChoice card) {
        super(card);
    }

    @Override
    public DisorientingChoice copy() {
        return new DisorientingChoice(this);
    }
}

class DisorientingChoiceEffect extends OneShotEffect {

    DisorientingChoiceEffect() {
        super(Outcome.Benefit);
        staticText = "For each opponent, choose up to one target artifact or enchantment that player controls. "
                + "For each permanent chosen this way, its controller may exile it. Then if one or more of the "
                + "chosen permanents are still on the battlefield, you search your library for up to that many "
                + "land cards, put them onto the battlefield tapped, then shuffle.";
    }

    private DisorientingChoiceEffect(final DisorientingChoiceEffect effect) {
        super(effect);
    }

    @Override
    public DisorientingChoiceEffect copy() {
        return new DisorientingChoiceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Cards permanentsToExile = new CardsImpl();
        int numberOfLandsToFetch = 0;
        if (controller == null) {
            return false;
        }
        List<Permanent> permanents = source
                .getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        for (Permanent permanent : permanents) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player == null) {
                continue;
            }
            if (player.chooseUse(outcome, "Exile " + permanent.getLogName() + "? If you don't, "
                    + controller.getName() + " will get a land from their library.", source, game)) {
                game.informPlayers(player.getLogName() + " chose to have their " + permanent.getLogName() + " exiled.");
                permanentsToExile.add(permanent);
            } else {
                game.informPlayers(player.getLogName() + " chose to have " + controller.getName() + " get a land.");
                numberOfLandsToFetch += 1;
            }
        }
        /*
        When Disorienting Choice resolves, the next opponent in turn order that controls a permanent chosen with
        Disorienting Choice decides whether or not to exile that permanent. Then each other opponent in turn order
        does the same. Then all appropriate permanents are exiled simultaneously. Opponents will know what choices
        opponents earlier in the turn order made.
         */
        controller.moveCards(permanentsToExile, Zone.EXILED, source, game);
        if (numberOfLandsToFetch > 0) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, numberOfLandsToFetch, new FilterLandCard());
            if (controller.searchLibrary(target, source, game)) {
                for (UUID cardId : target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
            }
        }
        return true;
    }
}
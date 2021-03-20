package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class DecoyGambit extends CardImpl {

    public DecoyGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // For each opponent, choose up to one target creature that player controls, 
        // then return that creature to its owner's hand unless its controller has you draw a card.
        this.getSpellAbility().addEffect(new DecoyGambitEffect());
        this.getSpellAbility().setTargetAdjuster(DecoyGambitAdjuster.instance);
    }

    private DecoyGambit(final DecoyGambit card) {
        super(card);
    }

    @Override
    public DecoyGambit copy() {
        return new DecoyGambit(this);
    }
}

enum DecoyGambitAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        game.getOpponents(ability.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .forEachOrdered(player -> {
                    FilterPermanent filter = new FilterCreaturePermanent(
                            "creature controlled by " + player.getName()
                    );
                    filter.add(new ControllerIdPredicate(player.getId()));
                    ability.addTarget(new TargetPermanent(0, 1, filter, false));
                });
    }
}

class DecoyGambitEffect extends OneShotEffect {

    DecoyGambitEffect() {
        super(Outcome.Benefit);
        staticText = "For each opponent, choose up to one target creature that player controls, "
                + "then return that creature to its owner's hand unless its controller has you draw a card.";
    }

    private DecoyGambitEffect(final DecoyGambitEffect effect) {
        super(effect);
    }

    @Override
    public DecoyGambitEffect copy() {
        return new DecoyGambitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Cards permanentToHand = new CardsImpl();
        int numberOfCardsToDraw = 0;
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
            if (player.chooseUse(outcome, "Have " + controller.getName() + " draw a card? If you don't, "
                    + permanent.getName() + " will be returned to its owner's hand.", source, game)) {
                game.informPlayers(player.getLogName() + " chose to have " + controller.getName() + " draw a card.");
                numberOfCardsToDraw += 1;
            } else {
                game.informPlayers(player.getLogName() + " chose to have their creature returned to their hand.");
                permanentToHand.add(permanent);
            }
        }
        /*
        As the Decoy Gambit resolves, first the next opponent in turn order (or, if it’s an opponent’s 
        turn, the opponent whose turn it is) chooses whether you’ll draw a card or return their creature 
        that was targeted to its owner’s hand, then each other opponent in turn order does so knowing 
        the choices made before them. After all choices are made, you draw the appropriate number of 
        cards. After you’ve drawn, the appropriate creatures are all simultaneously returned to their owners’ hands.
         */
        controller.drawCards(numberOfCardsToDraw, source, game);
        controller.moveCards(permanentToHand, Zone.HAND, source, game);
        return true;
    }
}

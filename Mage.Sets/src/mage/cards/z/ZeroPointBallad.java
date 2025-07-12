package mage.cards.z;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardIdPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInGraveyard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class ZeroPointBallad extends CardImpl {

    public ZeroPointBallad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}");

        // Destroy all creatures with toughness X or less. You lose X life. If X is 6 or more, return a creature card put into a graveyard this way to the battlefield under your control.
        this.getSpellAbility().addEffect(new ZeroPointBalladEffect());
    }

    private ZeroPointBallad(final ZeroPointBallad card) {
        super(card);
    }

    @Override
    public ZeroPointBallad copy() {
        return new ZeroPointBallad(this);
    }
}

class ZeroPointBalladEffect extends OneShotEffect {

    ZeroPointBalladEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all creatures with toughness X or less. "
                + "You lose X life. "
                + "If X is 6 or more, return a creature card put into a graveyard "
                + "this way to the battlefield under your control.";
    }

    private ZeroPointBalladEffect(final ZeroPointBalladEffect effect) {
        super(effect);
    }

    @Override
    public ZeroPointBalladEffect copy() {
        return new ZeroPointBalladEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        int xValue = GetXValue.instance.calculate(game, source, this);

        Cards putIntoGraveyardThisWay = new CardsImpl();

        // Destroy all creatures with toughness X or less.
        FilterPermanent filterToDestroy = new FilterCreaturePermanent();
        filterToDestroy.add(new ToughnessPredicate(ComparisonType.OR_LESS, xValue));
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filterToDestroy, controller.getId(), source, game)) {
            permanent.destroy(source, game, false);
            if (game.getState().getZone(permanent.getId()) == Zone.GRAVEYARD) {
                putIntoGraveyardThisWay.add(permanent);
            }
        }

        // You lose X life.
        game.processAction();
        new LoseLifeSourceControllerEffect(xValue).apply(game, source);

        if (xValue < 6) {
            return true;
        }

        // If X is 6 or more, return a creature card put into a graveyard
        // this way to the battlefield under your control.
        game.processAction();
        MageObject sourceObject = game.getObject(source);
        String sourceName = sourceObject != null ? sourceObject.getLogName() : "";
        FilterCard filter = new FilterCreatureCard("creature card put into a graveyard with " + sourceName);
        List<Predicate<MageObject>> cardIdPredicates = new ArrayList<>();
        for (UUID cardId : putIntoGraveyardThisWay) {
            cardIdPredicates.add(new CardIdPredicate(cardId));
        }
        filter.add(Predicates.or(cardIdPredicates));
        Target target = new TargetCardInGraveyard(filter);
        target.withNotTarget(true);
        if (!controller.chooseTarget(Outcome.PutCreatureInPlay, target, source, game)) {
            return true;
        }
        Card cardToReturn = game.getCard(target.getFirstTarget());
        if (cardToReturn == null) {
            return true;
        }
        controller.moveCards(cardToReturn, Zone.BATTLEFIELD, source, game);
        return true;
    }
}

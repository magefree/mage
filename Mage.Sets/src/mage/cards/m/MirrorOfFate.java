
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author North
 */
public final class MirrorOfFate extends CardImpl {

    public MirrorOfFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // {tap}, Sacrifice Mirror of Fate: Choose up to seven face-up exiled cards you own. Exile all the cards from your library, then put the chosen cards on top of your library.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new MirrorOfFateEffect(),
                new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MirrorOfFate(final MirrorOfFate card) {
        super(card);
    }

    @Override
    public MirrorOfFate copy() {
        return new MirrorOfFate(this);
    }
}

class MirrorOfFateEffect extends OneShotEffect {

    public MirrorOfFateEffect() {
        super(Outcome.Neutral);
        this.staticText = "Choose up to seven face-up exiled cards you own. Exile all the cards from your library, then put the chosen cards on top of your library";
    }

    public MirrorOfFateEffect(final MirrorOfFateEffect effect) {
        super(effect);
    }

    @Override
    public MirrorOfFateEffect copy() {
        return new MirrorOfFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // Choose up to seven face-up exiled cards you own
        CardsImpl cards = new CardsImpl();
        MirrorOfFateTarget targetExile = new MirrorOfFateTarget();
        if (controller.choose(outcome, targetExile, source, game)) {
            cards.addAll(targetExile.getTargets());
        }
        // Exile all the cards from your library
        controller.moveCards(new CardsImpl(controller.getLibrary().getCardList()), Zone.EXILED, source, game);
        // put the chosen cards on top of your library"
        return controller.putCardsOnTopOfLibrary(cards, game, source, true);
    }
}

class FaceUpPredicate implements Predicate<Card> {

    @Override
    public boolean apply(Card input, Game game) {
        return !input.isFaceDown(game);
    }

    @Override
    public String toString() {
        return "FaceUp";
    }
}

class MirrorOfFateTarget extends TargetCardInExile {

    public MirrorOfFateTarget() {
        super(0, 7, new FilterCard(), null);
        filter.add(new FaceUpPredicate());
        this.targetName = "face-up exiled cards you own";
    }

    public MirrorOfFateTarget(final MirrorOfFateTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        Card card = game.getCard(id);
        if (card != null && card.isOwnedBy(source.getControllerId())
                && game.getState().getZone(card.getId()) == Zone.EXILED) {
            for (ExileZone exile : game.getExile().getExileZones()) {
                if (exile != null && exile.contains(id)) {
                    return filter.match(card, source.getControllerId(), game);
                }
            }
        }
        return false;
    }

    @Override
    public MirrorOfFateTarget copy() {
        return new MirrorOfFateTarget(this);
    }
}

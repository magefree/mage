
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInExile;

/**
 *
 * @author LevelX2
 */
public final class PullFromEternity extends CardImpl {

    private static final FilterCard filter = new FilterCard("face-up exiled card");

    static {
        filter.add(Predicates.not(FaceDownPredicate.instance));
    }

    public PullFromEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Put target face-up exiled card into its owner's graveyard.
        this.getSpellAbility().addEffect(new PullFromEternityEffect());
        this.getSpellAbility().addTarget(new TargetCardInExile(1,1,filter, null, true));

    }

    private PullFromEternity(final PullFromEternity card) {
        super(card);
    }

    @Override
    public PullFromEternity copy() {
        return new PullFromEternity(this);
    }
}

class PullFromEternityEffect extends OneShotEffect {

    public PullFromEternityEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target face-up exiled card into its owner's graveyard.";
    }

    private PullFromEternityEffect(final PullFromEternityEffect effect) {
        super(effect);
    }

    @Override
    public PullFromEternityEffect copy() {
        return new PullFromEternityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(getTargetPointer().getFirst(game, source));
            if (card != null) {
                controller.moveCards(card, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}

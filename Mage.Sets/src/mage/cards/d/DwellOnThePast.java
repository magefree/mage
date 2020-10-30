
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
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
 * @author fireshoes
 */
public final class DwellOnThePast extends CardImpl {

    public DwellOnThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{G}");

        // Target player shuffles up to four target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new DwellOnThePastEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new DwellOnThePastTarget());

    }

    public DwellOnThePast(final DwellOnThePast card) {
        super(card);
    }

    @Override
    public DwellOnThePast copy() {
        return new DwellOnThePast(this);
    }
}

class DwellOnThePastEffect extends OneShotEffect {

    public DwellOnThePastEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles up to four target cards from their graveyard into their library";
    }

    public DwellOnThePastEffect(final DwellOnThePastEffect effect) {
        super(effect);
    }

    @Override
    public DwellOnThePastEffect copy() {
        return new DwellOnThePastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getFirstTarget());
        if (controller != null) {
            return controller.shuffleCardsToLibrary(new CardsImpl(source.getTargets().get(1).getTargets()), game, source);
        }
        return false;
    }
}

class DwellOnThePastTarget extends TargetCardInGraveyard {

    public DwellOnThePastTarget() {
        super(0, 4, new FilterCard("cards from target player's graveyard"));
    }

    public DwellOnThePastTarget(final DwellOnThePastTarget target) {
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
    public DwellOnThePastTarget copy() {
        return new DwellOnThePastTarget(this);
    }
}

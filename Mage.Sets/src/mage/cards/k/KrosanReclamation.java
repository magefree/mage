
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
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
public final class KrosanReclamation extends CardImpl {

    public KrosanReclamation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Target player shuffles up to two target cards from their graveyard into their library.
        this.getSpellAbility().addEffect(new KrosanReclamationEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new KrosanReclamationTarget());

        // Flashback {1}{G}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{1}{G}"), TimingRule.INSTANT));
    }

    private KrosanReclamation(final KrosanReclamation card) {
        super(card);
    }

    @Override
    public KrosanReclamation copy() {
        return new KrosanReclamation(this);
    }
}

class KrosanReclamationEffect extends OneShotEffect {

    public KrosanReclamationEffect() {
        super(Outcome.Neutral);
        this.staticText = "Target player shuffles up to two target cards from their graveyard into their library";
    }

    public KrosanReclamationEffect(final KrosanReclamationEffect effect) {
        super(effect);
    }

    @Override
    public KrosanReclamationEffect copy() {
        return new KrosanReclamationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            return targetPlayer.shuffleCardsToLibrary(new CardsImpl(source.getTargets().get(1).getTargets()), game, source);
        }
        return false;
    }
}

class KrosanReclamationTarget extends TargetCardInGraveyard {

    public KrosanReclamationTarget() {
        super(0, 2, new FilterCard());
    }

    public KrosanReclamationTarget(final KrosanReclamationTarget target) {
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
    public KrosanReclamationTarget copy() {
        return new KrosanReclamationTarget(this);
    }
}

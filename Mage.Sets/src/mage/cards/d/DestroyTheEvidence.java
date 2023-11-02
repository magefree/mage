
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class DestroyTheEvidence extends CardImpl {

    public DestroyTheEvidence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Destroy target land. Its controller reveals cards from the top of his
        // or her library until they reveal a land card, then puts those cards into their graveyard.
        TargetLandPermanent target = new TargetLandPermanent();
        this.getSpellAbility().addTarget(target);
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DestroyTheEvidenceEffect());
    }

    private DestroyTheEvidence(final DestroyTheEvidence card) {
        super(card);
    }

    @Override
    public DestroyTheEvidence copy() {
        return new DestroyTheEvidence(this);
    }
}

class DestroyTheEvidenceEffect extends OneShotEffect {

    public DestroyTheEvidenceEffect() {
        super(Outcome.Discard);
        this.staticText = "Its controller reveals cards from the top of their library until they reveal a land card, then puts those cards into their graveyard";
    }

    private DestroyTheEvidenceEffect(final DestroyTheEvidenceEffect effect) {
        super(effect);
    }

    @Override
    public DestroyTheEvidenceEffect copy() {
        return new DestroyTheEvidenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent landPermanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (landPermanent != null) {
            Player player = game.getPlayer(landPermanent.getControllerId());
            if (player == null) {
                return false;
            }
            Cards cards = new CardsImpl();
            for (Card card : player.getLibrary().getCards(game)) {
                if (card != null) {
                    cards.add(card);
                    if (card.isLand(game)) {
                        break;
                    }
                }
            }
            player.revealCards(source, cards, game);
            player.moveCards(cards, Zone.GRAVEYARD, source, game);
            return true;
        }
        return false;
    }
}

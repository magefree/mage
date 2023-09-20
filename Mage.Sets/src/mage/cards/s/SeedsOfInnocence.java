
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public final class SeedsOfInnocence extends CardImpl {

    public SeedsOfInnocence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}{G}");

        // Destroy all artifacts. They can't be regenerated. The controller of each of those artifacts gains life equal to its converted mana cost.
        this.getSpellAbility().addEffect(new SeedsOfInnocenceEffect());

    }

    private SeedsOfInnocence(final SeedsOfInnocence card) {
        super(card);
    }

    @Override
    public SeedsOfInnocence copy() {
        return new SeedsOfInnocence(this);
    }
}

class SeedsOfInnocenceEffect extends OneShotEffect {

    public SeedsOfInnocenceEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all artifacts. They can't be regenerated. The controller of each of those artifacts gains life equal to its mana value";
    }

    private SeedsOfInnocenceEffect(final SeedsOfInnocenceEffect effect) {
        super(effect);
    }

    @Override
    public SeedsOfInnocenceEffect copy() {
        return new SeedsOfInnocenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent artifact : game.getState().getBattlefield().getActivePermanents(new FilterArtifactPermanent(), controller.getId(), game)) {
                Player artifactController = game.getPlayer(artifact.getControllerId());
                int cmc = artifact.getManaValue();
                if (artifact.destroy(source, game, true)) {
                    if(artifactController != null) {
                        artifactController.gainLife(cmc, game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

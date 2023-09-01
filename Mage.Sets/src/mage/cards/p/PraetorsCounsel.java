
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class PraetorsCounsel extends CardImpl {

    public PraetorsCounsel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{G}{G}{G}");

        // Return all cards from your graveyard to your hand. Exile Praetor's Counsel. You have no maximum hand size for the rest of the game.
        this.getSpellAbility().addEffect(new PraetorsCounselEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
        this.getSpellAbility().addEffect(new MaximumHandSizeControllerEffect(Integer.MAX_VALUE, Duration.EndOfGame, HandSizeModification.SET));
    }

    private PraetorsCounsel(final PraetorsCounsel card) {
        super(card);
    }

    @Override
    public PraetorsCounsel copy() {
        return new PraetorsCounsel(this);
    }

}

class PraetorsCounselEffect extends OneShotEffect {

    public PraetorsCounselEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Return all cards from your graveyard to your hand";
    }

    private PraetorsCounselEffect(final PraetorsCounselEffect effect) {
        super(effect);
    }

    @Override
    public PraetorsCounselEffect copy() {
        return new PraetorsCounselEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.moveCards(controller.getGraveyard(), Zone.HAND, source, game);
            return true;
        }
        return false;
    }

}

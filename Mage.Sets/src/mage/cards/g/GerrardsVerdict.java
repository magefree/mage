
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class GerrardsVerdict extends CardImpl {

    public GerrardsVerdict(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}{B}");


        // Target player discards two cards. You gain 3 life for each land card discarded this way.
        this.getSpellAbility().addEffect(new GerrardsVerdictEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private GerrardsVerdict(final GerrardsVerdict card) {
        super(card);
    }

    @Override
    public GerrardsVerdict copy() {
        return new GerrardsVerdict(this);
    }
}

class GerrardsVerdictEffect extends OneShotEffect {

    public GerrardsVerdictEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target player discards two cards. You gain 3 life for each land card discarded this way";
    }

    private GerrardsVerdictEffect(final GerrardsVerdictEffect effect) {
        super(effect);
    }

    @Override
    public GerrardsVerdictEffect copy() {
        return new GerrardsVerdictEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            controller.gainLife(targetPlayer.discard(2, false, false, source, game).count(new FilterLandCard(), game) * 3, game, source);
            return true;
        }
        return false;
    }
}

package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElephantToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GenerousGift extends CardImpl {

    public GenerousGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Destroy target permanent. Its controller creates a 3/3 green Elephant creature token.
        this.getSpellAbility().addEffect(new GenerousGiftEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private GenerousGift(final GenerousGift card) {
        super(card);
    }

    @Override
    public GenerousGift copy() {
        return new GenerousGift(this);
    }
}

class GenerousGiftEffect extends OneShotEffect {

    GenerousGiftEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy target permanent. Its controller creates a 3/3 green Elephant creature token.";
    }

    private GenerousGiftEffect(final GenerousGiftEffect effect) {
        super(effect);
    }

    @Override
    public GenerousGiftEffect copy() {
        return new GenerousGiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        permanent.destroy(source, game, false);
        if (player == null) {
            return false;
        }
        Effect effect = new CreateTokenTargetEffect(new ElephantToken());
        effect.setTargetPointer(new FixedTarget(player.getId(), game));
        return effect.apply(game, source);
    }
}
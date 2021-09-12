package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FadingHope extends CardImpl {

    public FadingHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Return target creature to its owner's hand. If its mana value was 3 or less, scry 1.
        this.getSpellAbility().addEffect(new FadingHopeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FadingHope(final FadingHope card) {
        super(card);
    }

    @Override
    public FadingHope copy() {
        return new FadingHope(this);
    }
}

class FadingHopeEffect extends OneShotEffect {

    FadingHopeEffect() {
        super(Outcome.Benefit);
        staticText = "return target creature to its owner's hand. If its mana value was 3 or less, scry 1";
    }

    private FadingHopeEffect(final FadingHopeEffect effect) {
        super(effect);
    }

    @Override
    public FadingHopeEffect copy() {
        return new FadingHopeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        boolean flag = permanent.getManaValue() <= 3;
        player.moveCards(permanent, Zone.HAND, source, game);
        if (flag) {
            player.scry(1, source, game);
        }
        return true;
    }
}

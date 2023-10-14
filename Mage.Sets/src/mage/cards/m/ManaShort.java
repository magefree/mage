package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.common.TapAllTargetPlayerControlsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author Quercitron
 */
public final class ManaShort extends CardImpl {

    public ManaShort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Tap all lands target player controls and empty their mana pool.
        this.getSpellAbility().addEffect(new ManaShortEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private ManaShort(final ManaShort card) {
        super(card);
    }

    @Override
    public ManaShort copy() {
        return new ManaShort(this);
    }
}

class ManaShortEffect extends TapAllTargetPlayerControlsEffect {

    public ManaShortEffect() {
        super(StaticFilters.FILTER_LANDS);
        staticText = "Tap all lands target player controls and that player loses all unspent mana";
    }

    private ManaShortEffect(final ManaShortEffect effect) {
        super(effect);
    }

    @Override
    public ManaShortEffect copy() {
        return new ManaShortEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            super.apply(game, source);
            targetPlayer.getManaPool().emptyPool(game);
            return true;
        }
        return false;
    }
}

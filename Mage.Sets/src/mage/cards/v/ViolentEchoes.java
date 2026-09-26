package mage.cards.v;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlaneswalker;

/**
 *
 * @author muz
 */
public final class ViolentEchoes extends CardImpl {

    public ViolentEchoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");
        
        // Violent Echoes deals 6 damage to target creature or planeswalker. If excess damage was dealt to that permanent this way, empower Jace X, where X is that excess damage.
        this.getSpellAbility().addEffect(new ViolentEchoesEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private ViolentEchoes(final ViolentEchoes card) {
        super(card);
    }

    @Override
    public ViolentEchoes copy() {
        return new ViolentEchoes(this);
    }
}

class ViolentEchoesEffect extends OneShotEffect {

    ViolentEchoesEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 6 damage to target creature or planeswalker. " +
            "If excess damage was dealt to that permanent this way, empower Jace X, where X is that excess damage.";
    }

    private ViolentEchoesEffect(final ViolentEchoesEffect effect) {
        super(effect);
    }

    @Override
    public ViolentEchoesEffect copy() {
        return new ViolentEchoesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = getTargetPointer().getFirst(game, source);
        Player player = game.getPlayer(targetId);
        if (player != null) {
            player.damage(6, source, game);
            return true;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int excess = permanent.damageWithExcess(6, source, game);
        if (excess > 0) {
            new EmpowerJaceEffect(excess).apply(game, source);
        }
        return true;
    }
}

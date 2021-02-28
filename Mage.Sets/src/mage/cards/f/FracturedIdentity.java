package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class FracturedIdentity extends CardImpl {

    public FracturedIdentity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{U}");

        // Exile target nonland permanent. Each player other than its controller creates a token that's a copy of it.
        this.getSpellAbility().addEffect(new FracturedIdentityEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private FracturedIdentity(final FracturedIdentity card) {
        super(card);
    }

    @Override
    public FracturedIdentity copy() {
        return new FracturedIdentity(this);
    }
}

class FracturedIdentityEffect extends OneShotEffect {

    FracturedIdentityEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile target nonland permanent. Each player other than its controller creates a token that's a copy of it";
    }

    private FracturedIdentityEffect(final FracturedIdentityEffect effect) {
        super(effect);
    }

    @Override
    public FracturedIdentityEffect copy() {
        return new FracturedIdentityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (player == null || permanent == null) {
            return false;
        }
        player.moveCards(permanent, Zone.EXILED, source, game);
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (permanent.isControlledBy(playerId)) {
                continue;
            }
            new CreateTokenCopyTargetEffect(
                    playerId, null, false
            ).setTargetPointer(new FixedTarget(permanent, game)).apply(game, source);
        }
        return true;
    }
}

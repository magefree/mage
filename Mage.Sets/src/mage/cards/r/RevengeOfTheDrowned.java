package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.ZombieDecayedToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RevengeOfTheDrowned extends CardImpl {

    public RevengeOfTheDrowned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Target creature's owner puts it on the top or bottom of their library. You create a 2/2 black Zombie creature token with decayed.
        this.getSpellAbility().addEffect(new RevengeOfTheDrownedEffect());
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieDecayedToken()).concatBy("You"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RevengeOfTheDrowned(final RevengeOfTheDrowned card) {
        super(card);
    }

    @Override
    public RevengeOfTheDrowned copy() {
        return new RevengeOfTheDrowned(this);
    }
}

class RevengeOfTheDrownedEffect extends OneShotEffect {

    RevengeOfTheDrownedEffect() {
        super(Outcome.Benefit);
        staticText = "target creature's owner puts it on the top or bottom of their library.";
    }

    private RevengeOfTheDrownedEffect(final RevengeOfTheDrownedEffect effect) {
        super(effect);
    }

    @Override
    public RevengeOfTheDrownedEffect copy() {
        return new RevengeOfTheDrownedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(source.getFirstTarget()));
        if (player == null) {
            return false;
        }
        if (player.chooseUse(Outcome.Detriment, "Put the targeted object on the top or bottom of your library?",
                "", "Top", "Bottom", source, game)) {
            return new PutOnLibraryTargetEffect(true).apply(game, source);
        }
        return new PutOnLibraryTargetEffect(false).apply(game, source);
    }
}


package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ShuffleSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class BeaconOfImmortality extends CardImpl {

    public BeaconOfImmortality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{W}");


        // Double target player's life total.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new BeaconOfImmortalityEffect());
        // Shuffle Beacon of Immortality into its owner's library.
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
    }

    private BeaconOfImmortality(final BeaconOfImmortality card) {
        super(card);
    }

    @Override
    public BeaconOfImmortality copy() {
        return new BeaconOfImmortality(this);
    }
}

class BeaconOfImmortalityEffect extends OneShotEffect {

    public BeaconOfImmortalityEffect() {
        super(Outcome.GainLife);
        this.staticText = "Double target player's life total";
    }

    private BeaconOfImmortalityEffect(final BeaconOfImmortalityEffect effect) {
        super(effect);
    }

    @Override
    public BeaconOfImmortalityEffect copy() {
        return new BeaconOfImmortalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            int amount = player.getLife();
            if (amount < 0) {
                player.loseLife(-amount, game, source, false);
                return true;
            }
            if (amount > 0) {
                player.gainLife(amount, game, source);
                return true;
            }
        }
        return false;
    }
}

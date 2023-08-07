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
import mage.game.turn.TurnMod;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class BeaconOfTomorrows extends CardImpl {

    public BeaconOfTomorrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{U}{U}");


        // Target player takes an extra turn after this one.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new BeaconOfTomorrowsEffect());

        // Shuffle Beacon of Tomorrows into its owner's library.
        this.getSpellAbility().addEffect(ShuffleSpellEffect.getInstance());
    }

    private BeaconOfTomorrows(final BeaconOfTomorrows card) {
        super(card);
    }

    @Override
    public BeaconOfTomorrows copy() {
        return new BeaconOfTomorrows(this);
    }
}

class BeaconOfTomorrowsEffect extends OneShotEffect {

    public BeaconOfTomorrowsEffect() {
        super(Outcome.ExtraTurn);
        staticText = "Target player takes an extra turn after this one";
    }

    public BeaconOfTomorrowsEffect(final BeaconOfTomorrowsEffect effect) {
        super(effect);
    }

    @Override
    public BeaconOfTomorrowsEffect copy() {
        return new BeaconOfTomorrowsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getFirstTarget() == null) {
            return false;
        }

        game.getState().getTurnMods().add(new TurnMod(source.getFirstTarget()).withExtraTurn());
        return true;
    }
}

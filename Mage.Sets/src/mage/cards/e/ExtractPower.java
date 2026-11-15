package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CastManaAdjustment;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ExtractPower extends CardImpl {

    public ExtractPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

        // Look at the top card of each player's library, then exile those cards face down. You may play them without paying their mana costs for as long as they remain exiled.
        this.getSpellAbility().addEffect(new ExtractPowerEffect());
    }

    private ExtractPower(final ExtractPower card) {
        super(card);
    }

    @Override
    public ExtractPower copy() {
        return new ExtractPower(this);
    }
}

class ExtractPowerEffect extends OneShotEffect {

    ExtractPowerEffect() {
        super(Outcome.Exile);
        staticText = "look at the top card of each player's library, then exile those cards face down. "
                + "You may play them without paying their mana costs for as long as they remain exiled";
    }

    private ExtractPowerEffect(final ExtractPowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(false, CastManaAdjustment.WITHOUT_PAYING_MANA_COST)
                    .setTargetPointer(new FixedTarget(playerId))
                    .apply(game, source);
        }
        return true;
    }

    @Override
    public ExtractPowerEffect copy() {
        return new ExtractPowerEffect(this);
    }
}

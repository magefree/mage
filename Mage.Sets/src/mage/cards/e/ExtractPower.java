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
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

public final class ExtractPower extends CardImpl {

    public ExtractPower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{U}");

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
        staticText = "Look at the top card of each player's library, then exile those cards face down. "
                + "You may play them without paying their mana costs for as long as they remain exiled";
    }

    private ExtractPowerEffect(final ExtractPowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Player player : game.getPlayers().values()) {
            ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect effect =
                    new ExileFaceDownTopNLibraryYouMayPlayAsLongAsExiledTargetEffect(
                            false, CastManaAdjustment.WITHOUT_PAYING_MANA_COST
                    );
            effect.setTargetPointer(new FixedTarget(player.getId()));
            effect.apply(game, source);
        }
        return true;
    }

    @Override
    public ExtractPowerEffect copy() {
        return new ExtractPowerEffect(this);
    }
}

package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author miesma
 */
public class RedSunsTwilight extends CardImpl {

    public RedSunsTwilight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Destroy up to X target artifacts.
        // If X is 5 or more, for each artifact destroyed this way, create a token that's a copy of it.
        // Those tokens gain haste. Exile them at the beginning of the next end step.
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().setTargetAdjuster(RedSunsTwilightAdjuster.instance);
        this.getSpellAbility().addEffect(new RedSunsTwilightEffect());
    }

    private RedSunsTwilight(final RedSunsTwilight card) {
        super(card);
    }

    @Override
    public RedSunsTwilight copy() {
        return new RedSunsTwilight(this);
    }
}

enum RedSunsTwilightAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        // Select up to X artifacts
        ability.addTarget(new TargetArtifactPermanent(0, xValue));
    }
}

class RedSunsTwilightEffect extends OneShotEffect {

    RedSunsTwilightEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy up to X target artifacts. " +
                "If X is 5 or more, for each artifact destroyed this way, create a token that's a copy of it. " +
                "Those tokens gain haste. Exile them at the beginning of the next end step.";
    }

    private RedSunsTwilightEffect(final mage.cards.r.RedSunsTwilightEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.r.RedSunsTwilightEffect copy() {
        return new mage.cards.r.RedSunsTwilightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        // Try to destroy the artifacts
        List<Permanent> destroyedArtifacts = new ArrayList<>();
        for (UUID targetID : this.targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetID);
            if (permanent != null) {
                if (permanent.destroy(source, game, false)) {
                    game.getState().processAction(game);
                    // Note which were destroyed
                    destroyedArtifacts.add(permanent);
                }
            }
        }
        if (xValue < 5) {
            return true;
        }
        // If x >= 5 create copies of each artifact destroyed with {this} until EOT
        for(Permanent destoyedArtifact : destroyedArtifacts) {
            // Copies gain haste
            CreateTokenCopyTargetEffect effect
                    = new CreateTokenCopyTargetEffect(player.getId(), null, true);
            effect.setUseLKI(true);
            effect.setSavedPermanent(destoyedArtifact);
            effect.apply(game, source);
            // Exile them at the beginning of the next end step
            effect.exileTokensCreatedAtNextEndStep(game, source);
        }
        return true;
    }
}

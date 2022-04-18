package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FatalLore extends CardImpl {

    public FatalLore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // An opponent chooses one —
        this.getSpellAbility().getModes().setModeChooser(TargetController.OPPONENT);

        // • You draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).setText("you draw three cards"));

        // • You destroy up to two target creatures that player controls. They can't be regenerated. That player draws up to three cards.
        this.getSpellAbility().addMode(new Mode(new DestroyTargetEffect(
                "you destroy up to two target creatures that player controls. " +
                        "They can't be regenerated", true
        )).addEffect(new FatalLoreEffect()));
        this.getSpellAbility().setTargetAdjuster(FatalLoreAdjuster.instance);
    }

    private FatalLore(final FatalLore card) {
        super(card);
    }

    @Override
    public FatalLore copy() {
        return new FatalLore(this);
    }
}

enum FatalLoreAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Mode mode = ability.getModes().getMode();
        if (mode.getEffects().stream().noneMatch(DestroyTargetEffect.class::isInstance)) {
            return;
        }
        UUID playerId = mode
                .getEffects()
                .stream()
                .findFirst()
                .filter(Objects::nonNull)
                .map(effect -> (UUID) effect.getValue("choosingPlayer"))
                .orElse(null);
        FilterPermanent filter = new FilterCreaturePermanent("creatures controlled by " + game.getPlayer(playerId).getName());
        filter.add(new ControllerIdPredicate(playerId));
        mode.getTargets().clear();
        mode.addTarget(new TargetPermanent(0, 2, filter));
    }
}

class FatalLoreEffect extends OneShotEffect {

    FatalLoreEffect() {
        super(Outcome.Benefit);
        staticText = "That player draws up to three cards";
    }

    private FatalLoreEffect(final FatalLoreEffect effect) {
        super(effect);
    }

    @Override
    public FatalLoreEffect copy() {
        return new FatalLoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer((UUID) getValue("choosingPlayer"));
        if (player == null) {
            return false;
        }
        int toDraw = player.getAmount(0, 3, "Choose how many cards to draw", game);
        return player.drawCards(toDraw, source, game) > 0;
    }
}

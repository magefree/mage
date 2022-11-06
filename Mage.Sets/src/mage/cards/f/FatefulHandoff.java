package mage.cards.f;

import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author weirddan455
 */
public final class FatefulHandoff extends CardImpl {

    public FatefulHandoff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // Draw cards equal to the mana value of target artifact or creature you control. An opponent gains control of that permanent.
        this.getSpellAbility().addEffect(new FatefulHandoffEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));
    }

    private FatefulHandoff(final FatefulHandoff card) {
        super(card);
    }

    @Override
    public FatefulHandoff copy() {
        return new FatefulHandoff(this);
    }
}

class FatefulHandoffEffect extends OneShotEffect {

    public FatefulHandoffEffect() {
        super(Outcome.Benefit);
        this.staticText = "Draw cards equal to the mana value of target artifact or creature you control. An opponent gains control of that permanent.";
    }

    private FatefulHandoffEffect(final FatefulHandoffEffect effect) {
        super(effect);
    }

    @Override
    public FatefulHandoffEffect copy() {
        return new FatefulHandoffEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || permanent == null) {
            return false;
        }
        controller.drawCards(permanent.getManaValue(), source, game);
        Player opponent;
        Set<UUID> opponents = game.getOpponents(controller.getId());
        if (opponents.size() == 1) {
            opponent = game.getPlayer(opponents.iterator().next());
        } else {
            TargetOpponent target = new TargetOpponent(true);
            controller.chooseTarget(Outcome.GainControl, target, source, game);
            opponent = game.getPlayer(target.getFirstTarget());
        }
        if (opponent != null) {
            game.addEffect(new FatefulHandoffControlEffect(permanent.getId(), opponent.getId()), source);
        }
        return true;
    }
}

class FatefulHandoffControlEffect extends ContinuousEffectImpl {

    private final UUID permanentId;
    private final UUID opponentId;

    public FatefulHandoffControlEffect(UUID permanentId, UUID opponentId) {
        super(Duration.EndOfGame, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.permanentId = permanentId;
        this.opponentId = opponentId;
    }

    private FatefulHandoffControlEffect(final FatefulHandoffControlEffect effect) {
        super(effect);
        this.permanentId = effect.permanentId;
        this.opponentId = effect.opponentId;
    }

    @Override
    public FatefulHandoffControlEffect copy() {
        return new FatefulHandoffControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(opponentId);
        Permanent permanent = game.getPermanent(permanentId);
        if (opponent != null && permanent != null) {
            permanent.changeControllerId(opponentId, game, source);
        } else {
            this.discard();
        }
        return true;
    }
}

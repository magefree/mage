package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnexpectedRequest extends CardImpl {

    public UnexpectedRequest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn. You may attach an Equipment you control to that creature. If you do, unattach it at the beginning of the next end step.
        this.getSpellAbility().addEffect(new GainControlTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapTargetEffect("Untap that creature"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn."));
        this.getSpellAbility().addEffect(new UnexpectedRequestAttachEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private UnexpectedRequest(final UnexpectedRequest card) {
        super(card);
    }

    @Override
    public UnexpectedRequest copy() {
        return new UnexpectedRequest(this);
    }
}

class UnexpectedRequestAttachEffect extends OneShotEffect {

    UnexpectedRequestAttachEffect() {
        super(Outcome.Benefit);
        staticText = "You may attach an Equipment you control to that creature. " +
                "If you do, unattach it at the beginning of the next end step";
    }

    private UnexpectedRequestAttachEffect(final UnexpectedRequestAttachEffect effect) {
        super(effect);
    }

    @Override
    public UnexpectedRequestAttachEffect copy() {
        return new UnexpectedRequestAttachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null || !game.getBattlefield().contains(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT,
                source.getControllerId(), source, game, 1
        )) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT, true
        );
        player.choose(outcome, target, source, game);
        Permanent equipment = game.getPermanent(target.getFirstTarget());
        if (equipment == null || !permanent.addAttachment(equipment.getId(), source, game)) {
            return false;
        }
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new UnexpectedRequestUnattachEffect(equipment, game)
        ), source);
        return true;
    }
}

class UnexpectedRequestUnattachEffect extends OneShotEffect {

    UnexpectedRequestUnattachEffect(Permanent permanent, Game game) {
        super(Outcome.Benefit);
        staticText = "unattach that equipment";
        this.setTargetPointer(new FixedTarget(permanent, game));
    }

    private UnexpectedRequestUnattachEffect(final UnexpectedRequestUnattachEffect effect) {
        super(effect);
    }

    @Override
    public UnexpectedRequestUnattachEffect copy() {
        return new UnexpectedRequestUnattachEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(game.getPermanent(getTargetPointer().getFirst(game, source)))
                .ifPresent(permanent -> permanent.unattach(game));
        return true;
    }
}

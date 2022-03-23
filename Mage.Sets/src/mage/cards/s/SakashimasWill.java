package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
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
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SakashimasWill extends CardImpl {

    public SakashimasWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Choose one. If you control a commander as you cast this spell, you may choose both.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both."
        );
        this.getSpellAbility().getModes().setMoreCondition(ControlACommanderCondition.instance);

        // • Target opponent chooses a creature they control. You gain control of it.
        this.getSpellAbility().addEffect(new SakashimasWillStealEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // • Choose a creature you control. Each other creautre you control becomes a copy of that creature until end of turn.
        this.getSpellAbility().addMode(new Mode(new SakashimasWillCopyEffect()));
    }

    private SakashimasWill(final SakashimasWill card) {
        super(card);
    }

    @Override
    public SakashimasWill copy() {
        return new SakashimasWill(this);
    }
}

class SakashimasWillStealEffect extends OneShotEffect {

    SakashimasWillStealEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent chooses a creature they control. You gain control of it";
    }

    private SakashimasWillStealEffect(final SakashimasWillStealEffect effect) {
        super(effect);
    }

    @Override
    public SakashimasWillStealEffect copy() {
        return new SakashimasWillStealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        if (!target.canChoose(player.getId(), source, game)) {
            return false;
        }
        player.choose(outcome, target, source, game);
        if (game.getPermanent(target.getFirstTarget()) == null) {
            return false;
        }
        game.addEffect(new GainControlTargetEffect(
                Duration.Custom, true, source.getControllerId()
        ).setTargetPointer(new FixedTarget(target.getFirstTarget(), game)), source);
        return true;
    }
}

class SakashimasWillCopyEffect extends OneShotEffect {

    SakashimasWillCopyEffect() {
        super(Outcome.Benefit);
        staticText = "choose a creature you control. Each other creautre you control becomes a copy of that creature until end of turn";
    }

    private SakashimasWillCopyEffect(final SakashimasWillCopyEffect effect) {
        super(effect);
    }

    @Override
    public SakashimasWillCopyEffect copy() {
        return new SakashimasWillCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        if (!target.canChoose(player.getId(), source, game)) {
            return false;
        }
        player.choose(outcome, target, source, game);
        Permanent chosenCreature = game.getPermanent(target.getFirstTarget());
        if (chosenCreature == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                player.getId(), source, game
        )) {
            if (permanent == null || permanent.getId().equals(chosenCreature.getId())) {
                continue;
            }
            game.copyPermanent(
                    Duration.EndOfTurn, chosenCreature, permanent.getId(), source, new EmptyCopyApplier()
            );
        }
        return true;
    }
}

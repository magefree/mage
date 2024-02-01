package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.CantBeSacrificedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.List;
import java.util.UUID;

/**
 * @author spillnerdev
 */
public final class CallForAid extends CardImpl {

    public CallForAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // Gain control of all creatures target opponent controls until end of turn. Untap those creatures. They gain haste until end of turn. You can't attack that player this turn. You can't sacrifice those creatures this turn.
        getSpellAbility().addTarget(new TargetOpponent());
        getSpellAbility().addEffect(new CallForAidEffect());
    }

    private CallForAid(final CallForAid card) {
        super(card);
    }

    @Override
    public CallForAid copy() {
        return new CallForAid(this);
    }
}


class CallForAidEffect extends OneShotEffect {

    public CallForAidEffect() {
        super(Outcome.Benefit);
        this.staticText = "Gain control of all creatures target opponent controls until end of turn. Untap those creatures. They gain haste until end of turn. You can't attack that player this turn. You can't sacrifice those creatures this turn.";
    }

    protected CallForAidEffect(CallForAidEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent == null) {
            return false;
        }

        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(opponent.getId()));
        List<Permanent> opponentCreatures = game.getBattlefield().getActivePermanents(filter, opponent.getId(), game);
        filter = new FilterCreaturePermanent();
        filter.add(new PermanentReferenceInCollectionPredicate(opponentCreatures, game));

        //"Gain control of all creatures target opponent controls until end of turn"
        GainControlAllEffect gainControl = new GainControlAllEffect(Duration.EndOfTurn, filter);
        gainControl.apply(game, source);

        //"Untap those creatures".
        new UntapAllEffect(filter).apply(game, source);

        //"They gain haste until end of turn"
        GainAbilityAllEffect gainHaste = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filter);
        game.addEffect(gainHaste, source);

        //"You can't attack that player this turn"
        game.addEffect(new CantAttackTargetOpponentEffect(Duration.EndOfTurn).setTargetPointer(targetPointer), source);

        //"You can't sacrifice those creatures this turn"
        GainAbilityAllEffect cantBeSacrificed = new GainAbilityAllEffect(new SimpleStaticAbility(new CantBeSacrificedSourceEffect()), Duration.EndOfTurn, filter);
        game.addEffect(cantBeSacrificed, source);

        return true;
    }

    @Override
    public CallForAidEffect copy() {
        return new CallForAidEffect(this);
    }
}

class CantAttackTargetOpponentEffect extends RestrictionEffect {
    CantAttackTargetOpponentEffect(Duration duration) {
        super(duration, Outcome.Detriment);
    }

    private CantAttackTargetOpponentEffect(final CantAttackTargetOpponentEffect effect) {
        super(effect);
    }

    @Override
    public CantAttackTargetOpponentEffect copy() {
        return new CantAttackTargetOpponentEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        // Effect applies to all permanents (most likely creatures) controlled by the caster of "Call for Aid"
        return source.getControllerId().equals(permanent.getControllerId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null || attacker == null) {
            return true;
        }
        // Should be only a single target in case of "Call for Aid"
        return !this.getTargetPointer().getTargets(game,source).contains(defenderId);
    }

}

package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainControlAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author spillnerdev, xenohedron
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

    CallForAidEffect() {
        super(Outcome.Benefit);
        this.staticText = "Gain control of all creatures target opponent controls until end of turn. " +
                "Untap those creatures. They gain haste until end of turn. " +
                "You can't attack that player this turn. You can't sacrifice those creatures this turn.";
    }

    private CallForAidEffect(CallForAidEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent == null) {
            return false;
        }

        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(new ControllerIdPredicate(opponent.getId()));
        List<Permanent> opponentCreatures = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        filter = new FilterPermanent();
        filter.add(new PermanentReferenceInCollectionPredicate(opponentCreatures, game));

        //"Gain control of all creatures target opponent controls until end of turn"
        new GainControlAllEffect(Duration.EndOfTurn, filter).apply(game, source);
        game.processAction();

        //"Untap those creatures".
        new UntapAllEffect(filter).apply(game, source);

        //"They gain haste until end of turn"
        game.addEffect(new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filter), source);

        //"You can't attack that player this turn"
        game.addEffect(new CallForAidCantAttackThatPlayerEffect().setTargetPointer(this.getTargetPointer().copy()), source);

        //"You can't sacrifice those creatures this turn"
        game.addEffect(new CallForAidYouCantSacrificeEffect(source.getControllerId(), filter), source);

        return true;
    }

    @Override
    public CallForAidEffect copy() {
        return new CallForAidEffect(this);
    }
}

class CallForAidCantAttackThatPlayerEffect extends RestrictionEffect {

    CallForAidCantAttackThatPlayerEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
    }

    private CallForAidCantAttackThatPlayerEffect(final CallForAidCantAttackThatPlayerEffect effect) {
        super(effect);
    }

    @Override
    public CallForAidCantAttackThatPlayerEffect copy() {
        return new CallForAidCantAttackThatPlayerEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        // Effect applies to all permanents (most likely creatures) controlled by the caster of "Call for Aid"
        return Objects.equals(source.getControllerId(), permanent.getControllerId());
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

class CallForAidYouCantSacrificeEffect extends ContinuousEffectImpl {

    private final UUID playerId; // controller who is prevented from sacrificing
    private final FilterPermanent filter; // filter containing permanents that can't be sacrificed

    CallForAidYouCantSacrificeEffect(UUID playerId, FilterPermanent filter) {
        super(Duration.EndOfTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.playerId = playerId;
        this.filter = filter;
    }

    private CallForAidYouCantSacrificeEffect(final CallForAidYouCantSacrificeEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
        this.filter = effect.filter;
    }

    @Override
    public CallForAidYouCantSacrificeEffect copy() {
        return new CallForAidYouCantSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (permanent.getControllerId().equals(playerId)) {
                permanent.setCanBeSacrificed(false);
            }
        }
        return true;
    }

}

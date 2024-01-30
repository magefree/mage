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
import mage.filter.predicate.Predicates;
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
        PermanentReferenceInCollectionPredicate stolenPermanentsPredicate = new PermanentReferenceInCollectionPredicate(opponentCreatures, game);
        filter.add(stolenPermanentsPredicate);
        //... gain control of them till end of turn...
        GainControlAllEffect gainControl = new GainControlAllEffect(Duration.EndOfTurn, filter);
        gainControl.apply(game, source);
        //...untap all stolen creatures...
        new UntapAllEffect(filter).apply(game, source);
        //...give them haste...
        GainAbilityAllEffect gainHaste = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filter);
        game.addEffect(gainHaste, source);
        //...make sure the caster cant attack targeted opponent...
        FilterCreaturePermanent currentCreaturesFilter = new FilterCreaturePermanent();
        // At the time this filter it built we do not yet control the creatures of target opponent. So we have to combine the predicates.
        currentCreaturesFilter.add(Predicates.or(new ControllerIdPredicate(source.getControllerId()), stolenPermanentsPredicate));

        GainAbilityAllEffect cantAttackOwnerEffect = new GainAbilityAllEffect(new SimpleStaticAbility(new CantAttackCertainOpponentEffect(opponent)), Duration.EndOfTurn, currentCreaturesFilter);
        game.addEffect(cantAttackOwnerEffect, source);
        //...and finally prevent them from being sacrificed.
        GainAbilityAllEffect cantBeSacrificed = new GainAbilityAllEffect(new SimpleStaticAbility(new CantBeSacrificedSourceEffect()), Duration.EndOfTurn, filter);
        game.addEffect(cantBeSacrificed, source);

        return true;
    }

    @Override
    public CallForAidEffect copy() {
        return new CallForAidEffect(this);
    }
}

class CantAttackCertainOpponentEffect extends RestrictionEffect {

    private final Player opponent;

    // The target is chosen during the resolution of the spell itself.
    // However, the effect "lingers" until end of turn, so we need to remember which player was chosen as the target.
    CantAttackCertainOpponentEffect(Player opponent) {
        super(Duration.EndOfTurn, Outcome.Detriment);
        this.opponent = opponent;
    }

    private CantAttackCertainOpponentEffect(final CantAttackCertainOpponentEffect effect) {
        super(effect);
        this.opponent = effect.opponent;
    }

    @Override
    public CantAttackCertainOpponentEffect copy() {
        return new CantAttackCertainOpponentEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null || attacker == null) {
            return true;
        }
        return !defenderId.equals(opponent.getId());
    }

}

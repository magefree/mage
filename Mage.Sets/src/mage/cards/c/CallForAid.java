package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
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
import mage.filter.predicate.permanent.PermanentInListPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
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
        UUID targetOpponent = getTargetPointer().getFirst(game, source);
        if (targetOpponent != null) {
            // First we get all creatures of target player...
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            List<Permanent> opponentCreatures = game.getBattlefield().getActivePermanents(filter, targetOpponent, game);
            filter.add(new PermanentInListPredicate(opponentCreatures));
            //... gain control of them till end of turn...
            GainControlAllEffect gainControl = new GainControlAllEffect(Duration.EndOfTurn, filter);
            gainControl.apply(game, source);
            //...untap all stolen creatures...
            new UntapAllEffect(filter).apply(game,source);
            //...give them haste...
            GainAbilityAllEffect gainHaste = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, filter);
            game.addEffect(gainHaste, source);
            //...make sure they can't attack their owner...
            GainAbilityAllEffect cantAttackOwnerEffect = new GainAbilityAllEffect(new SimpleStaticAbility(new CantAttackOwnerEffect()), Duration.EndOfTurn, filter);
            game.addEffect(cantAttackOwnerEffect, source);
            //...and finally prevent them from being sacrificed.
            GainAbilityAllEffect cantBeSacrificed = new GainAbilityAllEffect(new SimpleStaticAbility(new CantBeSacrificedSourceEffect()), Duration.EndOfTurn, filter);
            game.addEffect(cantBeSacrificed, source);

            return true;
        }

        return false;
    }

    @Override
    public Effect copy() {
        return new CallForAidEffect(this);
    }
}

// Almost identical to ElrondOfTheWhiteCouncil#CantAttackItsOwnerEffect. Maybe these should be consolidated? (2024-01-29)
class CantAttackOwnerEffect extends RestrictionEffect {

    CantAttackOwnerEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "{this} can't attack its owner";
    }

    private CantAttackOwnerEffect(final CantAttackOwnerEffect effect) {
        super(effect);
    }

    @Override
    public CantAttackOwnerEffect copy() {
        return new CantAttackOwnerEffect(this);
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
        return !defenderId.equals(attacker.getOwnerId());
    }

}

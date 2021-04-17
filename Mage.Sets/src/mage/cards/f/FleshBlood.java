package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

public final class FleshBlood extends SplitCard {

    public FleshBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{G}", "{R}{G}", SpellAbilityType.SPLIT_FUSED);

        // Flesh
        // Exile target creature card from a graveyard. Put X +1/+1 counters on target creature, where X is the power of the card you exiled.
        Target target = new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE);
        getLeftHalfCard().getSpellAbility().addTarget(target);
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        getLeftHalfCard().getSpellAbility().addEffect(new FleshEffect());

        // Blood
        // Target creature you control deals damage equal to its power to any target.
        getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        getRightHalfCard().getSpellAbility().addTarget(new TargetAnyTarget());
        getRightHalfCard().getSpellAbility().addEffect(new BloodEffect());
    }

    private FleshBlood(final FleshBlood card) {
        super(card);
    }

    @Override
    public FleshBlood copy() {
        return new FleshBlood(this);
    }
}

class FleshEffect extends OneShotEffect {

    public FleshEffect() {
        super(Outcome.BoostCreature);
        staticText = "Exile target creature card from a graveyard. Put X +1/+1 counters on target creature, where X is the power of the card you exiled";
    }

    public FleshEffect(final FleshEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card targetCard = game.getCard(source.getFirstTarget());
        if (player == null || targetCard == null) {
            return false;
        }
        int power = targetCard.getPower().getValue();
        player.moveCards(targetCard, Zone.EXILED, source, game);
        if (power > 0) {
            Permanent targetCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
            if (targetCreature != null) {
                targetCreature.addCounters(CounterType.P1P1.createInstance(power), source.getControllerId(), source, game);
            }
        }
        return true;
    }

    @Override
    public FleshEffect copy() {
        return new FleshEffect(this);
    }

}

class BloodEffect extends OneShotEffect {

    public BloodEffect() {
        super(Outcome.Damage);
        staticText = "Target creature you control deals damage equal to its power to any target";
    }

    public BloodEffect(final BloodEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getFirstTarget());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }

        Permanent targetCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (sourcePermanent != null && targetCreature != null) {
            targetCreature.damage(sourcePermanent.getPower().getValue(), sourcePermanent.getId(), source, game, false, true);
            return true;
        }
        Player targetPlayer = game.getPlayer(source.getTargets().get(1).getFirstTarget());
        if (sourcePermanent != null && targetPlayer != null) {
            targetPlayer.damage(sourcePermanent.getPower().getValue(), sourcePermanent.getId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public BloodEffect copy() {
        return new BloodEffect(this);
    }

}

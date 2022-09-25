package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
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
        getLeftHalfCard().getSpellAbility().addEffect(new FleshEffect());
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCardInGraveyard(StaticFilters.FILTER_CARD_CREATURE).withChooseHint("to exile"));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("to put X +1/+1 counters on"));


        // Blood
        // Target creature you control deals damage equal to its power to any target.
        getRightHalfCard().getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent().withChooseHint("to deal damage equal to its power"));
        getRightHalfCard().getSpellAbility().addTarget(new TargetAnyTarget().withChooseHint("to deal damage to"));
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

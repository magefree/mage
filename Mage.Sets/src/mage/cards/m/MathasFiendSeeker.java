
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class MathasFiendSeeker extends CardImpl {

    private static final String rule = "For as long as that creature has a bounty counter on it, it has \"When this creature dies, each opponent draws a card and gains 2 life.\"";

    public MathasFiendSeeker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // At the beginning of your end step, put a bounty counter on target creature an opponent controls. For as long as that creature has a bounty counter on it, it has "When this creature dies, each opponent draws a card and gains 2 life."
        Ability ability = new BeginningOfYourEndStepTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), false);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        Ability ability2 = new DiesSourceTriggeredAbility(new DrawCardAllEffect(1, TargetController.OPPONENT));
        ability2.addEffect(new OpponentsGainLifeEffect());
        Effect effect = new MathasFiendSeekerGainAbilityEffect(ability2, Duration.Custom, rule);
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private MathasFiendSeeker(final MathasFiendSeeker card) {
        super(card);
    }

    @Override
    public MathasFiendSeeker copy() {
        return new MathasFiendSeeker(this);
    }
}

class MathasFiendSeekerGainAbilityEffect extends GainAbilityTargetEffect {

    public MathasFiendSeekerGainAbilityEffect(Ability ability, Duration duration, String rule) {
        super(ability, duration, rule);
    }

    public MathasFiendSeekerGainAbilityEffect(final MathasFiendSeekerGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        Permanent creature = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (creature != null && creature.getCounters(game).getCount(CounterType.BOUNTY) < 1) {
            return true;
        }
        return false;
    }

    @Override
    public MathasFiendSeekerGainAbilityEffect copy() {
        return new MathasFiendSeekerGainAbilityEffect(this);
    }
}

class OpponentsGainLifeEffect extends OneShotEffect {

    public OpponentsGainLifeEffect() {
        super(Outcome.GainLife);
        staticText = "and gains 2 life.";
    }

    public OpponentsGainLifeEffect(final OpponentsGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public OpponentsGainLifeEffect copy() {
        return new OpponentsGainLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && game.isOpponent(player, source.getControllerId())) {
                player.gainLife(2, game, source);
            }
        }
        return true;
    }

}

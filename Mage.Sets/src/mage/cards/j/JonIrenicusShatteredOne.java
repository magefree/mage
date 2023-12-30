package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.CantBeSacrificedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar, xenohedron
 */
public final class JonIrenicusShatteredOne extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you own but don't control");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public JonIrenicusShatteredOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{B}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, target opponent gains control of up to one target creature you control. Put two +1/+1 counters on it and tap it.
        // It's goaded for the rest of the game and it gains “This creature can't be sacrificed.”
        Ability ability = new BeginningOfEndStepTriggeredAbility(new JonIrenicusShatteredOneEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        ability.addTarget(new TargetControlledCreaturePermanent(0, 1));
        this.addAbility(ability);

        // Whenever a creature you own but don't control attacks, you draw a card.
        this.addAbility(new AttacksAllTriggeredAbility(new DrawCardSourceControllerEffect(1, "you"), false, filter, SetTargetPointer.NONE, false));
    }

    private JonIrenicusShatteredOne(final JonIrenicusShatteredOne card) {super(card);}

    @Override
    public JonIrenicusShatteredOne copy() {return new JonIrenicusShatteredOne(this);}
}


class JonIrenicusShatteredOneEffect extends OneShotEffect {

    JonIrenicusShatteredOneEffect() {
        super(Outcome.Detriment);
        this.staticText = "target opponent gains control of up to one target creature you control. Put two +1/+1 counters on it and tap it. " +
                "It's goaded for the rest of the game and it gains \"This creature can't be sacrificed.\"";
    }

    private JonIrenicusShatteredOneEffect(final JonIrenicusShatteredOneEffect effect) {
        super(effect);
    }

    @Override
    public JonIrenicusShatteredOneEffect copy() {
        return new JonIrenicusShatteredOneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getTargets().get(0).getFirstTarget());
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature == null || opponent == null) {
            return false;
        }
        ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame, opponent.getId());
        effect.setTargetPointer(new FixedTarget(creature, game));
        game.addEffect(effect, source);
        game.getState().processAction(game);
        creature.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game);
        creature.tap(source, game);
        game.addEffect(new GoadTargetEffect()
                .setDuration(Duration.EndOfGame)
                .setTargetPointer(new FixedTarget(creature, game)),
                source
        );
        game.addEffect(new GainAbilityTargetEffect(
                new SimpleStaticAbility(new CantBeSacrificedSourceEffect().setText("This creature can't be sacrificed")),
                Duration.Custom
        ).setTargetPointer(new FixedTarget(creature, game)), source);
        return true;
    }
}

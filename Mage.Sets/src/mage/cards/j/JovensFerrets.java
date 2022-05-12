package mage.cards.j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EndOfCombatTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.BlockedAttackerWatcher;

/**
 *
 * @author noahg
 */
public final class JovensFerrets extends CardImpl {

    public JovensFerrets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        
        this.subtype.add(SubType.FERRET);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Joven's Ferrets attacks, it gets +0/+2 until end of turn.
        Effect boostSourceEffect = new BoostSourceEffect(0, 2, Duration.EndOfTurn);
        boostSourceEffect.setText("it gets +0/+2 until end of turn");
        this.addAbility(new AttacksTriggeredAbility(boostSourceEffect, false));

        // At end of combat, tap all creatures that blocked Joven's Ferrets this turn. They don't untap during their controller's next untap step.
        Ability eocAbility = new EndOfCombatTriggeredAbility(new JovensFerretsEffect(), false);
        eocAbility.addWatcher(new BlockedAttackerWatcher());
        this.addAbility(eocAbility);
    }

    private JovensFerrets(final JovensFerrets card) {
        super(card);
    }

    @Override
    public JovensFerrets copy() {
        return new JovensFerrets(this);
    }
}

class JovensFerretsEffect extends OneShotEffect {

    public JovensFerretsEffect() {
        super(Outcome.Benefit);
    }

    public JovensFerretsEffect(final JovensFerretsEffect effect) {
        super(effect);
    }

    @Override
    public JovensFerretsEffect copy() {
        return new JovensFerretsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            BlockedAttackerWatcher watcher = game.getState().getWatcher(BlockedAttackerWatcher.class);
            if (watcher != null) {
                List<Permanent> toTap = new ArrayList<>();
                for (Permanent creature : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game)) {
                    if (!creature.getId().equals(source.getSourceId())) {
                        if (watcher.creatureHasBlockedAttacker(sourcePermanent, creature, game)) {
                            toTap.add(creature);
                        }
                    }
                }
                for (Permanent creature : toTap) {
                    creature.tap(source, game);
                    DontUntapInControllersNextUntapStepTargetEffect effect = new DontUntapInControllersNextUntapStepTargetEffect();
                    effect.setTargetPointer(new FixedTarget(creature.getId(), game));
                    game.addEffect(effect, source);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "tap all creatures that blocked {this} this turn. They don't untap during their controller's next untap step.";
    }
}
package mage.cards.j;

import java.util.UUID;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.ManaSpentDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Zelane
 */
public final class JadeOrbOfDragonkind extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("a Dragon creature spell");
    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public JadeOrbOfDragonkind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ARTIFACT }, "{2}{G}");

        // {T}: Add {G}.
        BasicManaAbility ability = new GreenManaAbility();
        // When you spend this mana to cast a Dragon creature spell, it enters the battlefield with an additional +1/+1 counter on it and gains hexproof until your next turn.
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new ManaSpentDelayedTriggeredAbility(new JadeOrbOfDragonkindCastEffect(), filter)));

        // Ability aa = new ManaSpentDelayedTriggeredAbility(new JadeOrbOfDragonkindCastEffect(), filter);
        // aa.addEffect(new JadeOrbOfDragonkindCastEffect());

        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    private JadeOrbOfDragonkind(final JadeOrbOfDragonkind card) {
        super(card);
    }

    @Override
    public JadeOrbOfDragonkind copy() {
        return new JadeOrbOfDragonkind(this);
    }
}

// Not one shot
class JadeOrbOfDragonkindCastEffect extends OneShotEffect {

    JadeOrbOfDragonkindCastEffect() {
        super(Outcome.Benefit);
        staticText = "it enters the battlefield with an additional +1/+1 counter on it " +
                "and gains hexproof until your next turn ";
    }

    public JadeOrbOfDragonkindCastEffect(final JadeOrbOfDragonkindCastEffect effect) {
        super(effect);
    }

    @Override
    public JadeOrbOfDragonkindCastEffect copy() {
        return new JadeOrbOfDragonkindCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        GameEvent event = ((ManaSpentDelayedTriggeredAbility) source).getTriggerEvent();
        if (event != null) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            // it enters the battlefield with an additional +1/+1 counter on it
            game.addEffect(
                    new AdditionalCounterEffect(
                            new MageObjectReference((spell).getSourceId(), spell.getZoneChangeCounter(game), game)),
                    source);
            // and gains hexproof until your next turn
            game.addEffect(
                    new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.UntilYourNextTurn, null, true)
                            .setTargetPointer(new FixedTarget(spell.getCard(), game)),
                    source);
        }
        return true;
    }
}

class AdditionalCounterEffect extends ReplacementEffectImpl {

    private final MageObjectReference mor;

    public AdditionalCounterEffect(MageObjectReference mor) {
        super(Duration.EndOfTurn, Outcome.BoostCreature);
        this.staticText = "it enters the battlefield with an additional +1/+1 counter on it";
        this.mor = mor;
    }

    public AdditionalCounterEffect(AdditionalCounterEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && mor.refersTo(permanent, game);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game,
                    event.getAppliedEffects());
        }
        return false;
    }

    @Override
    public AdditionalCounterEffect copy() {
        return new AdditionalCounterEffect(this);
    }
}

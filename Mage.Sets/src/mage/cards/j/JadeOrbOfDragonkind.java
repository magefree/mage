package mage.cards.j;

import java.util.UUID;

import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.ManaSpentDelayedTriggeredAbility;
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
        ManaSpentDelayedTriggeredAbility manaSpentAbility = new ManaSpentDelayedTriggeredAbility(
                new HexproofGainAbilityTargetEffect(), filter);
        manaSpentAbility.addEffect(new AdditionalCounterEffect());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(manaSpentAbility));

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

class HexproofGainAbilityTargetEffect extends GainAbilityTargetEffect {

    HexproofGainAbilityTargetEffect() {
        super(HexproofAbility.getInstance(), Duration.UntilYourNextTurn, null, true);
        staticText = "and gains hexproof until your next turn.";
    }

    public HexproofGainAbilityTargetEffect(final HexproofGainAbilityTargetEffect effect) {
        super(effect);
    }

    @Override
    public HexproofGainAbilityTargetEffect copy() {
        return new HexproofGainAbilityTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        GameEvent event = ((ManaSpentDelayedTriggeredAbility) source).getTriggerEvent();
        if (event != null) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            affectedObjectList.add(new MageObjectReference(spell.getSourceId(), game));
        }
    }
}

class AdditionalCounterEffect extends ReplacementEffectImpl {

    public AdditionalCounterEffect() {
        super(Duration.EndOfTurn, Outcome.BoostCreature);
        this.staticText = "it enters the battlefield with an additional +1/+1 counter on it";
    }

    public AdditionalCounterEffect(AdditionalCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (source instanceof ManaSpentDelayedTriggeredAbility == false) {
            return false;
        }
        GameEvent manaUsedEvent = ((ManaSpentDelayedTriggeredAbility) source).getTriggerEvent();
        Spell spell = game.getStack().getSpell(manaUsedEvent.getTargetId());
        if (spell == null) {
            return false;
        }
        return event.getSourceId() == spell.getSourceId();
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

package mage.cards.t;

import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TzaangorShaman extends CardImpl {

    public TzaangorShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");
        this.subtype.add(SubType.MUTANT, SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Sorcerous Elixir — Whenever Tzaangor Shaman deals combat damage to a player, copy the next instant or
        // sorcery spell you cast this turn when you cast it. You may choose new targets for the copy.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new TzaangorShamanAbility()).setText(" copy the next instant or sorcery spell you cast this turn when you cast it. You may choose new targets for the copy."), false).withFlavorWord("Sorcerous Elixir")
        );
    }

    private TzaangorShaman(final TzaangorShaman card) {
        super(card);
    }

    @Override
    public TzaangorShaman copy() {
        return new TzaangorShaman(this);
    }
}

class TzaangorShamanAbility extends DelayedTriggeredAbility {

    public TzaangorShamanAbility() {
        super(new CopyTargetSpellEffect(true), Duration.EndOfTurn, true, false);
    }

    public TzaangorShamanAbility(final TzaangorShamanAbility ability) {
        super(ability);
    }

    @Override
    public TzaangorShamanAbility copy() {
        return new TzaangorShamanAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        return true;
    }

    @Override
    public String getRule() {
        return "When you cast your next instant or sorcery this turn, copy that spell. " +
                "You may choose new targets for the copy.";
    }
}

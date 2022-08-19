package mage.cards.s;

import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.KickerAbility;
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
 * @author TheElk801
 */
public final class SeaGateStormcaller extends CardImpl {

    public SeaGateStormcaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Kicker {4}{U}
        this.addAbility(new KickerAbility("{4}{U}"));

        // When Sea Gate Stormcaller enters the battlefield, copy the next instant or sorcery spell with converted mana cost 2 or less you cast this turn when you cast it. If Sea Gate Stormcaller was kicked, copy that spell twice instead. You may choose new targets for the copies.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new CreateDelayedTriggeredAbilityEffect(new SeaGateStormcallerDelayedTriggeredAbility(true)),
                new CreateDelayedTriggeredAbilityEffect(new SeaGateStormcallerDelayedTriggeredAbility(false)),
                KickedCondition.ONCE, "copy the next instant or sorcery spell " +
                "with mana value 2 or less you cast this turn when you cast it. " +
                "If {this} was kicked, copy that spell twice instead. You may choose new targets for the copies."
        )));
    }

    private SeaGateStormcaller(final SeaGateStormcaller card) {
        super(card);
    }

    @Override
    public SeaGateStormcaller copy() {
        return new SeaGateStormcaller(this);
    }
}

class SeaGateStormcallerDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final boolean twice;

    public SeaGateStormcallerDelayedTriggeredAbility(boolean twice) {
        super(new CopyTargetSpellEffect(true), Duration.EndOfTurn, true, false);
        if (twice) {
            this.addEffect(new CopyTargetSpellEffect(true));
        }
        this.twice = twice;
    }

    public SeaGateStormcallerDelayedTriggeredAbility(final SeaGateStormcallerDelayedTriggeredAbility ability) {
        super(ability);
        this.twice = ability.twice;
    }

    @Override
    public SeaGateStormcallerDelayedTriggeredAbility copy() {
        return new SeaGateStormcallerDelayedTriggeredAbility(this);
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
        if (spell == null || !spell.isInstantOrSorcery(game) || spell.getManaValue() > 2) {
            return false;
        }
        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(event.getTargetId()));
        }
        return true;
    }

    @Override
    public String getRule() {
        return "When you cast your next instant or sorcery spell this turn with mana value 2 or less, " +
                "copy that spell" + (twice ? " twice" : "") +
                ". You may choose new targets for the cop" + (twice ? "ies" : "y") + ".";
    }
}

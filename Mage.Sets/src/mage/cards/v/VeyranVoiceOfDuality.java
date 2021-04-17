package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MagecraftAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VeyranVoiceOfDuality extends CardImpl {

    public VeyranVoiceOfDuality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EFREET);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Magecraft — Whenever you cast or copy an instant or sorcery spell, Veyran, Voice of Duality gets +1/+1 until end of turn.
        this.addAbility(new MagecraftAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn)));

        // If you casting or copying an instant or sorcery spell causes a triggered ability of a permanent you control to trigger, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new VeyranVoiceOfDualityEffect()));
    }

    private VeyranVoiceOfDuality(final VeyranVoiceOfDuality card) {
        super(card);
    }

    @Override
    public VeyranVoiceOfDuality copy() {
        return new VeyranVoiceOfDuality(this);
    }
}

class VeyranVoiceOfDualityEffect extends ReplacementEffectImpl {

    VeyranVoiceOfDualityEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if you casting or copying an instant or sorcery spell causes a triggered ability " +
                "of a permanent you control to trigger, that ability triggers an additional time";
    }

    private VeyranVoiceOfDualityEffect(final VeyranVoiceOfDualityEffect effect) {
        super(effect);
    }

    @Override
    public VeyranVoiceOfDualityEffect copy() {
        return new VeyranVoiceOfDualityEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
        if (sourceEvent == null) {
            return false;
        }
        if (sourceEvent.getType() != GameEvent.EventType.COPIED_STACKOBJECT
                && sourceEvent.getType() != GameEvent.EventType.SPELL_CAST) {
            return false;
        }
        // Only for entering artifacts or creatures
        Spell spell = game.getSpell(sourceEvent.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery()) {
            return false;
        }
        // Only for triggers of permanents
        return game.getPermanent(numberOfTriggersEvent.getSourceId()) != null;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}

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
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VeyranVoiceOfDuality extends CardImpl {

    public VeyranVoiceOfDuality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
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
        staticText = "if you casting or copying an instant or sorcery spell causes a triggered ability "
                + "of a permanent you control to trigger, that ability triggers an additional time";
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
        GameEvent sourceEvent = numberOfTriggersEvent.getSourceEvent();
        if (sourceEvent == null) {
            return false;
        }

        if (sourceEvent.getType() == GameEvent.EventType.SPELL_CAST
                || sourceEvent.getType() == GameEvent.EventType.COPIED_STACKOBJECT) {
            Spell spell = game.getSpell(sourceEvent.getTargetId());
            Permanent permanent = game.getPermanent(((NumberOfTriggersEvent) event).getSourceId());
            return spell != null
                    && permanent != null
                    && spell.isInstantOrSorcery(game)
                    && spell.isControlledBy(source.getControllerId())
                    && permanent.isControlledBy(source.getControllerId());
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}

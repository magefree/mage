package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorlessPredicate;
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
public final class EchoesOfEternity extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a colorless spell");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public EchoesOfEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ENCHANTMENT}, "{3}{C}{C}{C}");

        this.subtype.add(SubType.ELDRAZI);

        // If a triggered ability of a colorless spell you control or another colorless permanent you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new EchoesOfEternityEffect()));

        // Whenever you cast a colorless spell, copy it. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CopyTargetStackObjectEffect(
                false, false, true
        ).withText("it"), filter, false, SetTargetPointer.SPELL));
    }

    private EchoesOfEternity(final EchoesOfEternity card) {
        super(card);
    }

    @Override
    public EchoesOfEternity copy() {
        return new EchoesOfEternity(this);
    }
}

class EchoesOfEternityEffect extends ReplacementEffectImpl {

    EchoesOfEternityEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a triggered ability of a colorless spell you control or another " +
                "colorless permanent you control triggers, that ability triggers an additional time";
    }

    private EchoesOfEternityEffect(final EchoesOfEternityEffect effect) {
        super(effect);
    }

    @Override
    public EchoesOfEternityEffect copy() {
        return new EchoesOfEternityEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(event instanceof NumberOfTriggersEvent)
                || !source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent != null && permanent.getColor(game).isColorless()
                && !permanent.getId().equals(source.getSourceId())) {
            return true;
        }
        Spell spell = game.getSpell(event.getSourceId());
        return spell != null && spell.getColor(game).isColorless();
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }
}

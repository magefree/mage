package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KataraTheFearless extends CardImpl {

    public KataraTheFearless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If a triggered ability of an Ally you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new KataraTheFearlessEffect()));
    }

    private KataraTheFearless(final KataraTheFearless card) {
        super(card);
    }

    @Override
    public KataraTheFearless copy() {
        return new KataraTheFearless(this);
    }
}

class KataraTheFearlessEffect extends ReplacementEffectImpl {

    KataraTheFearlessEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "if a triggered ability of an Ally you control triggers, that ability triggers an additional time";
    }

    private KataraTheFearlessEffect(final KataraTheFearlessEffect effect) {
        super(effect);
    }

    @Override
    public KataraTheFearlessEffect copy() {
        return new KataraTheFearlessEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && permanent.hasSubtype(SubType.ALLY, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}

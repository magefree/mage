package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class ClamIAm extends CardImpl {

    public ClamIAm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.CLAMFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // If you roll a 3 on a six-sided die, you may reroll that die.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ClamIAmEffect()));
    }

    private ClamIAm(final ClamIAm card) {
        super(card);
    }

    @Override
    public ClamIAm copy() {
        return new ClamIAm(this);
    }
}

class ClamIAmEffect extends ReplacementEffectImpl {

    ClamIAmEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you roll a 3 on a six-sided die, you may reroll that die";
    }

    ClamIAmEffect(final ClamIAmEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.REPLACE_ROLLED_DIE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public ClamIAmEffect copy() {
        return new ClamIAmEffect(this);
    }
}

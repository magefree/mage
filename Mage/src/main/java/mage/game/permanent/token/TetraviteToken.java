package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author spjspj
 */
public final class TetraviteToken extends TokenImpl {

    public TetraviteToken() {
        super("Tetravite Token", "1/1 colorless Tetravite artifact creature token with flying and \"This creature can't be enchanted.\"");
        cardType.add(CardType.CREATURE);
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.TETRAVITE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TetraviteTokenEffect()));
    }

    public TetraviteToken(final TetraviteToken token) {
        super(token);
    }

    public TetraviteToken copy() {
        return new TetraviteToken(this);
    }
}

class TetraviteTokenEffect extends ContinuousRuleModifyingEffectImpl {

    public TetraviteTokenEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "this creature can't be enchanted";
    }

    public TetraviteTokenEffect(final TetraviteTokenEffect effect) {
        super(effect);
    }

    @Override
    public TetraviteTokenEffect copy() {
        return new TetraviteTokenEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACH
                || event.getType() == GameEvent.EventType.STAY_ATTACHED;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }
}

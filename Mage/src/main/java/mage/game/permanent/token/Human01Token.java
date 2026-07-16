package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.MageInt;

/**
 * @author muz
 */
public final class Human01Token extends TokenImpl {

    public Human01Token() {
        super("Human 0/1 Token", "0/1 white Human creature token with \"Permanents can't phase in.\"");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.HUMAN);
        power = new MageInt(0);
        toughness = new MageInt(1);

        this.addAbility(new SimpleStaticAbility(new CantPhaseInEffect()));
    }

    private Human01Token(final Human01Token token) {
        super(token);
    }

    @Override
    public Human01Token copy() {
        return new Human01Token(this);
    }
}

class CantPhaseInEffect extends ContinuousRuleModifyingEffectImpl {

    CantPhaseInEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = "permanents can't phase in";
    }

    private CantPhaseInEffect(final CantPhaseInEffect effect) {
        super(effect);
    }

    @Override
    public CantPhaseInEffect copy() {
        return new CantPhaseInEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PHASE_IN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
}

package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;

/**
 *
 * @author weirddan455
 */
public final class DragToTheBottom extends CardImpl {

    public DragToTheBottom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Domain -- Each creature gets -X/-X until end of turn, where X is 1 plus the number of basic land types among lands you control.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                DragToTheBottomValue.instance,
                DragToTheBottomValue.instance,
                Duration.EndOfTurn
        ));
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
    }

    private DragToTheBottom(final DragToTheBottom card) {
        super(card);
    }

    @Override
    public DragToTheBottom copy() {
        return new DragToTheBottom(this);
    }
}

enum DragToTheBottomValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int value = 1 + DomainValue.REGULAR.calculate(game, sourceAbility, effect);
        return -value;
    }

    @Override
    public DynamicValue copy() {
        return this;
    }

    @Override
    public String toString() {
        return "-X";
    }

    @Override
    public String getMessage() {
        return "1 plus the number of basic land types among lands you control";
    }
}

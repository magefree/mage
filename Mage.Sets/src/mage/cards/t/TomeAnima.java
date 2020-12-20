package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

import java.util.UUID;

/**
 * @author arcox
 */
public final class TomeAnima extends CardImpl {

    public TomeAnima(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tome Anima can’t be blocked as long as you’ve drawn two or more cards this turn.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new CantBeBlockedSourceAbility(), Duration.WhileOnBattlefield),
                TomeAnimaCondition.instance,
                "{this} can't be blocked as long as you've drawn two or more cards this turn"
        )));
    }

    private TomeAnima(final TomeAnima card) {
        super(card);
    }

    @Override
    public TomeAnima copy() {
        return new TomeAnima(this);
    }
}

enum TomeAnimaCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CardsDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsDrawnThisTurnWatcher.class);
        return watcher != null && watcher.getCardsDrawnThisTurn(source.getControllerId()) > 1;
    }
}

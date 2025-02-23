package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.watchers.common.CastSpellLastTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NarsetJeskaiWaymaster extends CardImpl {

    public NarsetJeskaiWaymaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, you may discard your hand. If you do, draw cards equal to the number of spells you've cast this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(NarsetJeskaiWaymasterValue.instance), new DiscardHandCost()
        )).addHint(NarsetJeskaiWaymasterValue.getHint()));
    }

    private NarsetJeskaiWaymaster(final NarsetJeskaiWaymaster card) {
        super(card);
    }

    @Override
    public NarsetJeskaiWaymaster copy() {
        return new NarsetJeskaiWaymaster(this);
    }
}

enum NarsetJeskaiWaymasterValue implements DynamicValue {
    instance;
    private static final Hint hint = new ValueHint("Spells you've cast this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher == null ? 0 : watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(sourceAbility.getControllerId());
    }

    @Override
    public NarsetJeskaiWaymasterValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "the number of spells you've cast this turn";
    }

    @Override
    public String toString() {
        return "1";
    }
}

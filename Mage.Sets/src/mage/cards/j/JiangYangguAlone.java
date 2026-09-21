package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksPlayerAloneControlledTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.watchers.common.DiscardedCardWatcher;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class JiangYangguAlone extends CardImpl {

    public JiangYangguAlone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever a creature you control attacks a player alone, discard a card, then draw a card. Then put a +1/+1 counter on that creature for each card you've discarded this turn.
        Ability ability = new AttacksPlayerAloneControlledTriggeredAbility(new DiscardControllerEffect(1));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(", then"));
        ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(), JiangYangguAloneValue.instance)
            .setText("Then put a +1/+1 counter on that creature for each card you've discarded this turn"));
        this.addAbility(ability.addHint(JiangYangguAloneValue.getHint()), new DiscardedCardWatcher());
    }

    private JiangYangguAlone(final JiangYangguAlone card) {
        super(card);
    }

    @Override
    public JiangYangguAlone copy() {
        return new JiangYangguAlone(this);
    }
}

enum JiangYangguAloneValue implements DynamicValue {
    instance;

    private static final Hint hint = new ValueHint("Cards you've discarded this turn", instance);

    public static Hint getHint() {
        return hint;
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return DiscardedCardWatcher.getDiscarded(sourceAbility.getControllerId(), game);
    }

    @Override
    public JiangYangguAloneValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "card you've discarded this turn";
    }
}

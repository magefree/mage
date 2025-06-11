package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PromisingDuskmage extends CardImpl {

    public PromisingDuskmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Promising Duskmage dies, if it had a +1/+1 counter on it, draw a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(PromisingDuskmageCondition.instance));
    }

    private PromisingDuskmage(final PromisingDuskmage card) {
        super(card);
    }

    @Override
    public PromisingDuskmage copy() {
        return new PromisingDuskmage(this);
    }
}

enum PromisingDuskmageCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .getEffectValueFromAbility(source, "permanentLeftBattlefield", Permanent.class)
                .filter(permanent -> permanent.getCounters(game).containsKey(CounterType.P1P1))
                .isPresent();
    }

    @Override
    public String toString() {
        return "it had a +1/+1 counter on it";
    }
}

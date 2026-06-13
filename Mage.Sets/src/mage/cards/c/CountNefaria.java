package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.PermanentsSacrificedWatcher;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CountNefaria extends CardImpl {

    public CountNefaria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // This spell costs {3} less to cast if you've sacrificed a permanent this turn.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SpellCostReductionSourceEffect(3, CountNefariaCondition.instance)
        ).setRuleAtTheTop(true).addHint(CountNefariaCondition.getHint()), new PermanentsSacrificedWatcher());

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private CountNefaria(final CountNefaria card) {
        super(card);
    }

    @Override
    public CountNefaria copy() {
        return new CountNefaria(this);
    }
}

enum CountNefariaCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance, "You've sacrificed a permanent this turn");

    @Override
    public boolean apply(Game game, Ability source) {
        PermanentsSacrificedWatcher watcher = game.getState().getWatcher(PermanentsSacrificedWatcher.class);
        return watcher != null
            && !watcher.getThisTurnSacrificedPermanents(source.getControllerId()).isEmpty();
    }

    public static Hint getHint() {
        return hint;
    }
}

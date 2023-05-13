package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JuriMasterOfTheRevue extends CardImpl {

    public JuriMasterOfTheRevue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever you sacrifice a permanent, put a +1/+1 counter on Juri, Master of the Revue.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
        ));

        // When Juri dies, it deals damage equal its power to any target.
        Ability ability = new DiesSourceTriggeredAbility(new DamageTargetEffect(JuriMasterOfTheRevueValue.instance)
                .setText("it deals damage equal its power to any target"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private JuriMasterOfTheRevue(final JuriMasterOfTheRevue card) {
        super(card);
    }

    @Override
    public JuriMasterOfTheRevue copy() {
        return new JuriMasterOfTheRevue(this);
    }
}

enum JuriMasterOfTheRevueValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = (Permanent) effect.getValue("permanentLeftBattlefield");
        return permanent == null ? 0 : permanent.getPower().getValue();
    }

    @Override
    public JuriMasterOfTheRevueValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
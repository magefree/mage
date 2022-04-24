package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngelicObserver extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.CITIZEN, "Citizen you control");
    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Citizens you control", xValue);

    public AngelicObserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This spell costs {1} less to cast for each Citizen you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionForEachSourceEffect(1, xValue).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true).addHint(hint));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private AngelicObserver(final AngelicObserver card) {
        super(card);
    }

    @Override
    public AngelicObserver copy() {
        return new AngelicObserver(this);
    }
}

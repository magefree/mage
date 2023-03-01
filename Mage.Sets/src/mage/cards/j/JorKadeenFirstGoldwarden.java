package mage.cards.j;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.*;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.permanent.EquippedPredicate;

/**
 * @author TheElk801
 */
public final class JorKadeenFirstGoldwarden extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterPermanent();

    static {
        filter.add(EquippedPredicate.instance);
        filter2.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, null);
    private static final Hint hint = new ValueHint("Equipped creatures you control", xValue);
    private static final Condition condition = new SourceMatchesFilterCondition(filter2);

    public JorKadeenFirstGoldwarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Jor Kadeen, First Goldwarden attacks, it gets +X/+X until end of turn, where X is the number of equipped creatures you control. Then if Jor Kadeen's power is 4 or greater, draw a card.
        Ability ability = new AttacksTriggeredAbility(new BoostSourceEffect(
                xValue, xValue, Duration.EndOfTurn, true, "it"
        ));
        ability.addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                condition, "Then if {this}'s power is 4 or greater"
        ));
        this.addAbility(ability.addHint(hint));
    }

    private JorKadeenFirstGoldwarden(final JorKadeenFirstGoldwarden card) {
        super(card);
    }

    @Override
    public JorKadeenFirstGoldwarden copy() {
        return new JorKadeenFirstGoldwarden(this);
    }
}
